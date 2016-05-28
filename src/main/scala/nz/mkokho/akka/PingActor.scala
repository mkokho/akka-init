package nz.mkokho.akka

import scala.util.Random

import akka.actor.Actor
import akka.actor.Props
import com.typesafe.scalalogging.StrictLogging
import nz.mkokho.akka.PingActor.Ping

object PingActor {
  object Ping

  def props() = Props(new PingActor()).withDispatcher("ping-dispatcher")
}

class PingActor extends Actor with StrictLogging {

  override def receive = {
    case Ping =>
      val t = 500 //Random.nextInt(1000) + 500
      logger.info(s" ${self.path.toString.split("user")(1)} blocking for $t millis")
      Thread.sleep(t)
      logger.info(s"unblocking")
      sender() ! s"Time [$t]"
  }
}
