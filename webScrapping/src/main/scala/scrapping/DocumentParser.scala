package scrapping

import org.jsoup.Jsoup
import org.jsoup.nodes.{Document, Element}
import play.api.libs.json.{Format, Json}

import scala.jdk.CollectionConverters._

case class Applicant(name: String, front: String, votes: Int)

object Applicant {
  implicit val format: Format[Applicant] = Json.format[Applicant]
}

case class Constituency(
    id: Int,
    name: String,
    electorate: Int,
    votesPoled: Int,
    votingPercentage: Float,
    nota: Int,
    candidates: Seq[Applicant]
)

object Constituency {
  implicit val format: Format[Constituency] = Json.format[Constituency]
}

object DocumentParser {

  /**
    * For testing
    * val docAsString = io.Source.fromFile("page.html").getLines().mkString
    * val doc = Jsoup.parse(docAsString)
    * parse(doc)
    */
  private val table1Fields =
    Seq(
      "constituency",
      "electorate",
      "votesPoled",
      "votingPercentage",
      "nota"
    )

  private val table2Fields = Seq("name", "party", "front", "votes")

  private def extractTable1(table1: Element): Map[String, String] = {
    var mapFields: scala.collection.mutable.Map[String, String] =
      scala.collection.mutable.Map.empty

    table1.select("b").asScala.zip(table1Fields).map {
      case (el, "constituency") =>
        val data = el.text().split("\\.").map(_.trim)
        mapFields.addOne("id" -> data(0))
        mapFields.addOne("constituency" -> data(1))
      case (el, "electorate") =>
        val data = el.text().split(":").map(_.trim)
        mapFields.addOne("electorate", data(1))
      case (el, "votesPoled") =>
        val data = el.text().split(":").map(_.trim)
        mapFields.addOne("votesPoled" -> data(1))
      case (el, "votingPercentage") =>
        val data = el.text().split(":").map(_.trim)
        mapFields.addOne("votingPercentage" -> data(1))
      case (el, "nota") =>
        val data = el.text().split(":").map(_.trim)
        mapFields.addOne("nota" -> data(1))
      case (_, other) => println("Unknown entity -> " + other)
    }

    mapFields
  }.toMap

  def extractTable2(table2: Element): Seq[Map[String, String]] = {
    val candidateList
        : scala.collection.mutable.ListBuffer[Map[String, String]] =
      scala.collection.mutable.ListBuffer.empty

    table2.select("tr").asScala.drop(1).map { el =>
      val candidateMap: scala.collection.mutable.Map[String, String] =
        scala.collection.mutable.Map.empty

      el.select("td").asScala.zip(table2Fields).map {
        case (el, field) =>
          candidateMap.addOne(field -> el.text().trim)
      }
      candidateList.addOne(candidateMap.toMap)
    }

    candidateList.toSeq
  }

  def buildConsitunecy(
      fieldMap: Map[String, String],
      candidateList: Seq[Map[String, String]]
  ): Constituency = Constituency(
    id = fieldMap("id").toInt,
    name = fieldMap("constituency"),
    electorate = fieldMap("electorate").toInt,
    votesPoled = fieldMap("votesPoled").toInt,
    votingPercentage = fieldMap("votingPercentage").toFloat,
    nota = fieldMap("nota").toInt,
    candidates = candidateList.map { applicatMap =>
      Applicant(
        name = applicatMap("name"),
        front = applicatMap("front"),
        votes = applicatMap("votes").toInt
      )
    }.toSeq
  )

  def parse(doc: Document): Constituency = {
    val tables = doc.select("table").asScala.take(2)
    val table1 = tables(0)
    val table2 = tables(1)
    val fieldMap = extractTable1(table1)
    val candidateList = extractTable2(table2)
    buildConsitunecy(fieldMap, candidateList)
  }
}
