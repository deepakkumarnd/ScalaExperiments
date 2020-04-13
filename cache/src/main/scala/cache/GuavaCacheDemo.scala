package cache

import com.google.common.cache.{CacheBuilder, CacheLoader}

import scala.concurrent.{Await, Future}
import scala.concurrent.duration._
import scala.util.Random
import scala.concurrent.ExecutionContext.Implicits.global

object GuavaCache {
  private val cache = CacheBuilder
    .newBuilder()
    .build(
      new CacheLoader[String, Future[Double]] {
        def load(key: String): Future[Double] = Future(apicall(key))
      }
    )

  def lookup(key: String): Future[Double] = {
    cache.get(key)
  }

  def apicall(key: String): Double = {
    Thread.sleep(2000)
    Random.nextDouble()
  }
}

object GuavaCacheDemo extends App {

  val ans1 = GuavaCache.lookup("average-1")
  val ans2 = GuavaCache.lookup("average-2")
  val ans3 = GuavaCache.lookup("average-3")

  println(Await.result(ans1, 5.seconds))
  println(Await.result(ans2, 5.seconds))
  println(Await.result(ans3, 5.seconds))

  // Result already available in the cache
  println(cache.GuavaCache.lookup("average-1"))
}
