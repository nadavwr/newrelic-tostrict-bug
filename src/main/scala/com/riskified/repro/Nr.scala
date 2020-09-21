package com.riskified.repro

import akka.http.scaladsl.server._
import akka.http.scaladsl.server.Directives._
import com.newrelic.api.agent._
import scala.concurrent._

object Nr {

  def init(): Unit = {
    println(s"initial tx: $NoTxId")
  }

  val NoTx: Transaction = NewRelic.getAgent.getTransaction
  val NoTxId: Int = NoTx.hashCode

  def around(f: (() => Future[RouteResult]) => Future[RouteResult]): Directive0 =
    Directive(gen => ctx => f(() => gen(())(ctx)))

  case class TxCtx(id: Int) {
    override def toString: String = id match {
      case NoTxId => "N/A"
      case _      => id.toString
    }
  }

  private def getTxId(): TxCtx = {
    val tx = NewRelic.getAgent.getTransaction
    val txId = tx.hashCode
    TxCtx(txId)
  }

  private def report(label: String): Unit = {
    println(s"[$label]: ${getTxId()}")
  }

  def apply(label: String = ""): Directive0 =
    extractExecutionContext.flatMap { implicit ec =>
      around { resume =>
        report("pre-" + label)
        resume().andThen { case _ => report("post-" + label) }
      }
    }

}
