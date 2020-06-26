def methodAcceptingBlock[T](f: => T): T = f

methodAcceptingBlock {
  println("Block syntax")
}

methodAcceptingBlock("Using ()")

// task dsl

trait Task {
  def execute(): Unit
}

def task(desc: String, dependencies: Seq[Task] = Seq.empty[Task])(f: => Unit): Unit = {
  dependencies.foreach(_.execute())
  f
}

task("make coffee") {
  println("I don't know how to make a coffee")
}
