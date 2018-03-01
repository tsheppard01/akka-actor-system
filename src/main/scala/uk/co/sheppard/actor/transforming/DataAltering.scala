package uk.co.sheppard.actor.transforming

trait DataAltering {

  def alterData(inputData: String): Seq[String]
}

class StringReplacementDataAltering(stringToReplace: String,
                                    replacementString: String)
    extends DataAltering {
  override def alterData(inputData: String): Seq[String] =
      Seq(inputData.replace(stringToReplace, replacementString))
}

class StringSplitDataAltering(stringToSplitOn: String) extends DataAltering {

  private val INCLUDE_TRAILING_EMPTY_STRING = -1

  override def alterData(inputData: String): Seq[String] =
    inputData.split(stringToSplitOn, INCLUDE_TRAILING_EMPTY_STRING)
}