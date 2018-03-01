package uk.co.sheppard.actor.actors

import akka.actor.{Actor, ActorRef, Props}
import uk.co.sheppard.actor.actors.StringSplittingActor.SplitStringMessage
import uk.co.sheppard.actor.model.InputData
import uk.co.sheppard.actor.transforming.DataAltering

class StringSplittingActor(dataAltering: DataAltering, sinkActor: ActorRef) extends Actor {

  override def receive: Receive = {
    case SplitStringMessage(inputData) =>
      val splitData = dataAltering.alterData(inputData.data)
      splitData.map{ data =>
        SinkActor.SinkMessage(InputData(data, inputData.typeOfData))
      }.foreach{ message =>
        sinkActor ! message
      }
  }
}

object StringSplittingActor {
  def apply(dataAltering: DataAltering, sinkActor: ActorRef): Props = {Props(new StringSplittingActor(dataAltering, sinkActor))}

  final case class SplitStringMessage(inputData: InputData)
}
