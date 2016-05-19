package nz.mkokho.akka

import scala.io.StdIn
import scala.concurrent.duration._

import akka.actor.ActorSystem
import akka.actor.Props
import akka.pattern.ask
import akka.http.scaladsl.Http
import akka.http.scaladsl.server.Directives._
import akka.stream.ActorMaterializer
import akka.util.Timeout
import com.typesafe.scalalogging.StrictLogging


object AkkaHttpExample extends StrictLogging {
  def main(args: Array[String]) {

    implicit val system = ActorSystem("elki-system")
    implicit val materializer = ActorMaterializer()
    implicit val executionContext = system.dispatcher
    implicit val timeout: Timeout = 5 seconds

    val pingActor = ((0 to 40) map {x => x -> system.actorOf(PingActor.props(), s"ping-${x}") }).toMap

    var id: Int = 0

    val route =
     get {
        pathSingleSlash {
          logger.info(s"${this} received request $id")
          val closure = s"completed request $id"
          val res = pingActor(id) ? PingActor.Ping
          id += 1

          complete(res.mapTo[String] andThen {
            case _ => logger.info(closure)
          })
        }
      }

    val bindingFuture = Http().bindAndHandle(route, "localhost", 8080)

    println(s"Server online at http://localhost:8080/\nPress RETURN to stop...")
    StdIn.readLine() // let it run until user presses return
    bindingFuture
      .flatMap(_.unbind()) // trigger unbinding from the port
      .onComplete(_ ⇒ system.terminate()) // and shutdown when done
  }
}



