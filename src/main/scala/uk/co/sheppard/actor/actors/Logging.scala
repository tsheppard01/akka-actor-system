package uk.co.sheppard.actor.actors

trait Logging {

  val name = this.getClass.getSimpleName
  println(s"Started $name")

  def logThisStuff(message: String): Unit = {
    println(s"$name: $message")
  }
}
