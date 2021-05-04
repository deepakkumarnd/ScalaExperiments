package scrapping

import org.openqa.selenium.By.ByTagName
import org.openqa.selenium.WebElement
import org.openqa.selenium.chrome.{ChromeDriver, ChromeOptions}
import play.api.libs.json.{JsValue, Json}
import java.io.{BufferedWriter, FileWriter}
import scala.jdk.CollectionConverters._

object Spider extends App {

  System.setProperty(
    "webdriver.chrome.driver",
    "/Users/deepakkumar/bin/chromedriver"
  )

  val chromeOptions = new ChromeOptions()

  chromeOptions.setHeadless(true)

  println(chromeOptions.asMap())
  val driver = new ChromeDriver(chromeOptions)

  driver.get(
    "https://www.mathrubhumi.com/result/kerala-assembly-election-2021/indepth/election_results.html?districts=Kerala#map_view"
  )

  println(driver.getTitle)

  val elements = driver.findElementsByClassName("internal_winner").asScala

  val fields = Seq(
    "name",
    "front",
    "lead",
    "constituency",
    "party",
    "votes",
    "age",
    "gender"
  )

  val parsedData =
    elements
      .map { el =>
        val map: collection.mutable.Map[String, String] =
          collection.mutable.Map.empty[String, String]

        val img = el.findElement(new ByTagName("img"))
        map.update("image", img.getAttribute("src"))

        val ptags = el.findElements(new ByTagName("p")).asScala

        def spanText(ptag: WebElement): String =
          ptag.findElement(new ByTagName("span")).getText.trim

        ptags.zip(fields).foreach {
          case (ptag, "name")  => map.update("name", ptag.getText.trim)
          case (ptag, "front") => map.update("front", ptag.getText.trim)
          case (ptag, "party") => map.update("party", ptag.getText.trim)
          case (ptag, "lead")  => map.update("lead", spanText(ptag))
          case (ptag, "constituency") =>
            map.update("constituency", spanText(ptag))
          case (ptag, "votes") =>
            map.update("votes", spanText(ptag))
          case (ptag, "age") =>
            map.update("age", ptag.getText.split(":").last.trim)
          case (ptag, "gender") =>
            map.update("gender", ptag.getText.split(":").last.trim)
          case (_, unknown) => println("Unhandled field : " + unknown)
        }

        map
      }

  val json = Json.toJson(parsedData)

  writeToFile(json)

  def writeToFile(json: JsValue): Unit = {
    val fileWriter = new FileWriter("candidates.json")
    val buffer = new BufferedWriter(fileWriter)
    buffer.write(json.toString())
    buffer.close()
  }

  driver.close()
}
