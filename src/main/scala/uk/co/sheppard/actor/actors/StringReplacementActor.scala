package uk.co.sheppard.actor.actors

import akka.actor.{Actor, ActorRef, Props}
import uk.co.sheppard.actor.model.InputData
import uk.co.sheppard.actor.transforming.DataAltering

class StringReplacementActor(dataAltering: DataAltering,
                             forkAActor: ActorRef,
                             forkBActor: ActorRef)
    extends Actor with Logging {

  override def receive: Receive = {
    case StringReplacementActor.SecondActorMessage(inputData) =>
      val data = dataAltering.alterData(inputData.data).head

      if (inputData.typeOfData == "Type1")
        forkAActor ! SinkActor.SinkMessage(inputData)
      else
        forkBActor ! StringSplittingActor.SplitStringMessage(InputData(data, inputData.typeOfData))

  }
}

object StringReplacementActor {

  def apply(dataAltering: DataAltering,
            forkAActor: ActorRef,
            forkBActor: ActorRef): Props =
    Props(new StringReplacementActor(dataAltering, forkAActor, forkBActor))

  final case class SecondActorMessage(inputData: InputData)
}
