package scrapping

import play.api.libs.json.{JsString, Json}

import java.io.File
import java.net.URL
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.Source
import sys.process._
import scala.language.postfixOps
import scala.concurrent.duration._

object ImageDownloader extends App {

  val data = Source.fromFile("candidates.json").getLines().mkString
  val json = Json.parse(data)
  val targetDir = "images"
  implicit val ec: ExecutionContext = ExecutionContext.global

  def downloadImage(imageUrl: String): Future[Unit] =
    Future {
      val url = new URL(imageUrl)
      val filename = url.getFile
      val destination =
        targetDir + filename.substring(filename.lastIndexOf('/'))

      url #> new File(destination) !!

      println(s"Downloaded to $destination")
    }(ExecutionContext.global)

  val resuts = Future
    .sequence(
      (json \\ "image")
        .map {
          case JsString(url) => downloadImage(url)
          case _             => Future.successful { println("Invalid json") }
        }
    )
    .map(_ => println("Done"))

  Await.result(resuts, 60 seconds)
}
