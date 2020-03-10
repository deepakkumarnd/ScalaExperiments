package example

class Action {

  def apply(fn: () => Unit): Unit = {
    println("Running block")
    fn()
  }
}

object Block extends App {

  val myAction = new Action() {
    println("My action")
  }

}
