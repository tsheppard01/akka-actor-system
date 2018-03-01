package uk.co.sheppard.actor

import akka.actor.ActorSystem
import uk.co.sheppard.actor.actors.{
  DataSourceActor,
  SinkActor,
  StringSplittingActor,
  StringReplacementActor
}
import uk.co.sheppard.actor.io.{DataGenerator, DataSink}
import uk.co.sheppard.actor.transforming.StringReplacementDataAltering

import scala.concurrent.Await
import scala.concurrent.duration.Duration

/**
  * Simple application to show how to build an akka actor data processing chain
  * Contains several actors each acting on a single data record
  * Implements DoABunchOfStuffThenDie pattern
  */
object main {

  def main(arg: Array[String]): Unit = {

    val actorSystem = ActorSystem("SimpleActorSystem")

    val source = new DataGenerator
    val sink = new DataSink
    val dataAltering = new StringReplacementDataAltering("a", "z")

    val sinkActorRef = actorSystem.actorOf(SinkActor(sink), "ForkAActor")
    val stringSplitActorRef = actorSystem.actorOf(
      StringSplittingActor(dataAltering, sinkActorRef),
      "ForkBActor")
    val stringReplacementActorRef = actorSystem.actorOf(
      StringReplacementActor(dataAltering, sinkActorRef, stringSplitActorRef),
      "SecondActor")
    val dataSourceActorRef = actorSystem.actorOf(
      DataSourceActor(source, stringReplacementActorRef),
      "DataSourceActor")

    val inputMessages =
      for (i <- 1 to 100)
        yield DataSourceActor.GetDataMessage()

    inputMessages.foreach { message =>
      dataSourceActorRef ! message
    }

    Await.result(actorSystem.whenTerminated, Duration.Inf)
  }

}
