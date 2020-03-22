package example

object Block extends App {
  def Action(fn: => Unit) = {
    fn
  }

  Action {
    println("Hi this is a block")
  }
}