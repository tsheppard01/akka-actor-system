package uk.co.sheppard.actor.actors

import akka.actor.{Actor, ActorRef, Props}
import uk.co.sheppard.actor.actors.DataSourceActor.GetDataMessage
import uk.co.sheppard.actor.io.DataGenerator

/**
  * Actor to get data for processing
  *
  * @param source The source to get data from
  * @param secondActor The actor to send data to
  */
class DataSourceActor(source: DataGenerator, secondActor: ActorRef) extends Actor with Logging {

  override def receive: Receive = {
    case GetDataMessage() =>
      logThisStuff("Generating data")
      val dataRecord = source.getData

      logThisStuff("Sending data to second actor")
      secondActor ! StringReplacementActor.SecondActorMessage(dataRecord)
  }
}

object DataSourceActor {
  def apply(source: DataGenerator, secondActor: ActorRef): Props = Props(new DataSourceActor(source, secondActor))

  /**
    * Message the indicates actor should pass data to second actor
    */
  final case class GetDataMessage()
}
