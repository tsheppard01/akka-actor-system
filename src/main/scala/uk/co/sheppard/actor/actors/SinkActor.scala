package uk.co.sheppard.actor.actors

import akka.actor.{Actor, Props}
import uk.co.sheppard.actor.actors.SinkActor.SinkMessage
import uk.co.sheppard.actor.io.DataSink
import uk.co.sheppard.actor.model.InputData

class SinkActor(sink: DataSink) extends Actor with Logging {
  override def receive: Receive = {
    case SinkMessage(inputData) =>
      sink.sinkIt(inputData)
  }
}

object SinkActor {
  def apply(sink: DataSink): Props = Props(new SinkActor(sink))

  final case class SinkMessage(inputData: InputData)
}
