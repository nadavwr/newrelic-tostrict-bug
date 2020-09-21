package com.riskified.repro

import akka.actor.ActorSystem
import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.server.{HttpApp, Route}
import scala.concurrent.ExecutionContext
import scala.concurrent.duration._

object SampleApp extends HttpApp with App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = system.dispatcher

  override def routes: Route =
    (Nr("before strict") & toStrictEntity(1.second) & Nr("after strict")) {
      complete(StatusCodes.OK)
    }

  startServer("0.0.0.0", 8000)
}
