package scaffiene

import com.github.blemale.scaffeine.{AsyncLoadingCache, Scaffeine}

import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.Future
import scala.concurrent.duration._
import scala.language.postfixOps

object ScaffieneDemo extends App {
  val cache: AsyncLoadingCache[String, Int] = Scaffeine()
    .recordStats()
    .expireAfterWrite(1 second)
    .maximumSize(10)
    .buildAsyncFuture((key: String) => Future.successful(10))

  val x: Future[Int] = cache.get("deepak")

  x.map(println)

  Thread.sleep(1000)
}
