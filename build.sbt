name := "akka-init"

organization := "nz.mkokho"

scalaVersion := "2.11.8"

libraryDependencies ++= Seq(
  "com.typesafe.scala-logging" %% "scala-logging" % "3.4.0",
  "ch.qos.logback" % "logback-classic" % "1.1.7",
  "org.slf4j" % "slf4j-api" % "1.7.21",
  "com.typesafe.akka" %% "akka-slf4j" % "2.4.4",

  "com.typesafe.akka" %% "akka-http-core" % "2.4.4" % "compile, test",
  "com.typesafe.akka" %% "akka-http-experimental" % "2.4.4" % "compile, test",

  "com.typesafe.akka" %% "akka-http-testkit" % "2.4.4" % "compile, test",
  "org.scalatest" %% "scalatest" % "2.2.6" % "test"
)

packAutoSettings