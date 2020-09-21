name := "akka-http-client-repro"

version := "0.1"

scalaVersion := "2.12.12"

Compile / run / fork := true
Compile / run / javaOptions ++= List(
  "-Dnewrelic.config.app_name=newrelic-repro-nadavwr",
  "-Dnewrelic.config.agent_enabled=true",
  "-Dnewrelic.config.log_level=finest"
)

val newrelicVersion = "6.0.0"

enablePlugins(JavaAgent)
javaAgents += "com.newrelic.agent.java" % "newrelic-agent" % newrelicVersion % Runtime

libraryDependencies ++= List(
  "com.newrelic.agent.java" % "newrelic-api" % newrelicVersion,
  "com.typesafe.akka" %% "akka-http" % "10.1.5", // downgrade to 10.1.4 to see it work
  "com.typesafe.akka" %% "akka-stream" % "2.5.23"
)
