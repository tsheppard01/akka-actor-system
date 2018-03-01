package uk.co.sheppard.actor.io

import uk.co.sheppard.actor.model.InputData

class DataSink {

  def sinkIt(inputData: InputData): Unit = {
    println("Sunk it")
  }
}
