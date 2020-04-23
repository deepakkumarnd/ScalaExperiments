package example

import scala.concurrent.Future
import scala.concurrent.ExecutionContext.Implicits.global
import scala.util.{Failure, Success}

object future2 extends App {

  def makeTea: Future[Unit] = Future {
    println("Making tea started")
    Thread.sleep(2000)
    println("Making tea ended")
  }

  def makeCoffee: Future[Unit] = Future {
    println("Making coffee started")
    Thread.sleep(1000)
    println("Making coffee ended")
  }

  val f1 = makeTea
  val f2 = makeCoffee

  f1 onComplete {
    case Success(_) => println("Tea done")
    case Failure(_) => println("Tea failed")
  }

  f2 onComplete {
    case Success(_) => println("Coffee done")
    case Failure(_) => println("Coffee failed")
  }

  for {
    tea <- f1
    coffee <- f2
  } yield println("Done making tea and coffee")

  Thread.sleep(10000)
  println("OK")
}
