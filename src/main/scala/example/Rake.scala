package rake

import scala.concurrent.{Await, Future}
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

final class Task private (
    desc: String,
    dependencies: Seq[Task] = Seq.empty[Task],
    body: => Unit
) {
  var executed: Boolean = false

  def run(): Future[Unit] = {
    if (!executed) {
      println(s"Executing dependencies for $desc")
      Future.traverse(dependencies)(_.run()).map { _ =>
        body
        executed = true
      }
    } else Future.successful(())
  }
}

object Task {
  def task(desc: String, dependencies: Seq[Task] = List.empty[Task])(
      body: => Unit
  ): Task =
    new Task(desc, dependencies, body)
}

object RakeTest extends App {
  import Task._

  val boilWater = task("Boil water") {
    println("Boiling water ")
  }

  val boilMilk = task("Boil milk") {
    println("Boiling milk")
  }

  val mixWaterAndMilk =
    task("Mix milk and water", Seq(boilWater, boilMilk)) {
      println("Mixing milk and water")
    }

  val addCoffeePowder = task("Add coffee powder", Seq(mixWaterAndMilk)) {
    println("Adding coffee powder")
  }

  val addSugar = task("Add sugar", Seq(addCoffeePowder)) {
    println("Adding sugar")
  }

  val makeCoffee = task("Make coffee", Seq(addSugar)) {
    println("Coffee is ready to serve")
  }

  Await.result(makeCoffee.run(), 10.seconds)
}
