package uk.co.sheppard.actor.io

import java.nio.charset.StandardCharsets

import uk.co.sheppard.actor.model.InputData

import scala.util.Random

/**
  * Generates a 128 character utf8 string
  */
class DataGenerator {

  val randomBuffer = new Array[Byte](128)

  def getData: InputData = {
    Random.nextBytes(randomBuffer)
    InputData(new String(randomBuffer, StandardCharsets.UTF_8), "Type1")
  }
}
