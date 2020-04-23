package example

import scala.concurrent.{Await, ExecutionContext, Future}
import ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.util.Success

object future1 extends App {
  def getUser: Future[Unit] = Future {
    println("Started: Get User")
    Thread.sleep(2000)
    println("Finished: Get User")
  }

  def getAds: Future[Unit] = Future {
    println("Started: Get Ads")
    Thread.sleep(1000)
    println("Finished: Get Ads")
  }
  def allAds: Unit = {
    println("Testing started with for expression")

    val f1 = getUser
    val f2 = getAds

    f1.onComplete({ case Success(_) => println("Got user") })
    f2.onComplete({ case Success(_) => println("Got ads") })

    for {
      user <- f1
      ads <- f2
    } yield println("Testing Completed")

    Await.result(f1, 2.seconds)
    Await.result(f2, 2.seconds)
    println("All done")
  }

  allAds
}
