package scrapping

import akka.actor.ActorSystem
import akka.stream.scaladsl.{FileIO, Source}
import akka.util.ByteString
import org.jsoup._
import org.jsoup.nodes.Document
import play.api.libs.json.Json

import java.nio.file.Paths
import scala.concurrent.{ExecutionContext, Future}

object CrawlKeralaAssembyDotOrg extends App {
  implicit val system: ActorSystem = ActorSystem()
  implicit val ec: ExecutionContext = system.dispatcher

  final val year = 2021
  final val baseUrl =
    s"http://keralaassembly.org/election/2021/candidate_list.php?year=$year&no="

  val urlSource = Source(1 to 140).map(i => baseUrl + i.toString)
  def downloadDocument(url: String): Future[Document] = Future {
    Jsoup.connect(url).get()
  }

  val output = Paths.get("election-data.json")

  urlSource
    .mapAsync(8)(downloadDocument)
    .async
    .map(DocumentParser.parse)
    .async
    .map { c => ByteString(Json.toJson(c).toString() + "\n") }
    .runWith(FileIO.toPath(output))
    .recover { exception => exception.getMessage }
    .onComplete { _ =>
      system.terminate()
      println("Completed")
    }
}
