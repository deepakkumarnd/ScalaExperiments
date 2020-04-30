package wsclient

import akka.actor.ActorSystem
import akka.stream._
import play.api.libs.ws._
import play.api.libs.ws.ahc._
import play.api.libs.json.{JsPath, Reads, JsValue}
import play.api.libs.functional.syntax._
import scala.concurrent.Future
import play.api.libs.ws.JsonBodyReadables._
import scala.concurrent.ExecutionContext.Implicits._

// case class for the response fields
case class Person(
    name: String,
    height: String,
    mass: String,
    hair_color: String,
    skin_color: String
)

// configure implicit reads for the json to be mapped to the Person in the companion object.
object Person {
  implicit val personReads: Reads[Person] = (
    (JsPath \ "name").read[String] and
      (JsPath \ "height").read[String] and
      (JsPath \ "mass").read[String] and
      (JsPath \ "hair_color").read[String] and
      (JsPath \ "skin_color").read[String]
  )(Person.apply _)
}

object Main extends App {
  // Create Akka system for thread and streaming management
  val system = ActorSystem()
  system.registerOnTermination { System.exit(0) }

  // Create the standalone WS client
  // no argument defaults to a AhcWSClientConfig created from
  // "AhcWSClientConfigFactory.forConfig(ConfigFactory.load, this.getClass.getClassLoader)"
  val wsClient =
    StandaloneAhcWSClient()(SystemMaterializer(system).materializer)

  call(wsClient)
    .andThen { case _ => wsClient.close() }
    .andThen { case _ => system.terminate() }

  // call method to make the api call
  def call(wsClient: StandaloneWSClient): Future[Unit] = {
    wsClient
      .url("https://swapi.dev/api/people/1")
      .addHttpHeaders("Content-Type" -> "application/json")
      .get()
      .map { response =>
        val statusText: String = response.statusText
        println(statusText)

        try {
          val body = response.body[JsValue].as[Person]
          println("Got a response")
          println(body)
        } catch {
          case e =>
            println("Got some exception" + e.getClass)
        }
      }
  }
}
