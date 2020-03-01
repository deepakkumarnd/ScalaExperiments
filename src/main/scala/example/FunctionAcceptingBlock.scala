package example

object FunctionAcceptingBlock extends App {

  def assert[T](cond1: T, cond2: T): Boolean = {
    println("Running assert")
    cond1 == cond2
  }

  def checkFunction(name: String)(fn: => Any): Unit = {
    fn
  }

  //  checkFunction("My Random Check") {
  //    assert(1,2)
  //  }
  //  def boilWater: Unit = {
  //    println("Boiling water")
  //  }
  //
  //  def addMilk: Unit = {
  //    println("AddMilk")
  //  }

  def task(taskName: String, dependencies: List[String] = List())(body: => Any): () => Any = {
    dependencies.map((name) => this.getClass().getMethod(name).invoke(this))
    println(s"Running ${taskName}")
    () => { body }
  }

  def invoke(str: String): Unit = {

  }

  task("addMilk") {
    println("Add milk")
  }

  task("boilWater") {
    println("Boil water")
  }

  task("makeCoffee", List("boilWater", "addMilk")) {
    println("Coffee is ready")
  }

  invoke("makeCoffee")
}
