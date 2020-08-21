package scalawithcats.json1

sealed trait Json

final case class JsObject(get: Map[String, Json]) extends Json
final case class JsArray(get: Seq[Json]) extends Json
final case class JsString(get: String) extends Json
final case class JsNumber(get: Double) extends Json
final case class JsBoolean(get: Boolean) extends Json
case object JsNull extends Json

trait JsonWritable[T] {
  def write(value: T): Json
}

object JsonWritableObject {
  implicit val stringWriter: JsonWritable[String] = new JsonWritable[String] {
    override def write(value: String): Json = JsString(value)
  }

  implicit val numberWriter: JsonWritable[Double] = new JsonWritable[Double] {
    override def write(value: Double): Json = JsNumber(value)
  }

  implicit val booleanWriter: JsonWritable[Boolean] =
    new JsonWritable[Boolean] {
      override def write(value: Boolean): Json = JsBoolean(value)
    }
}

// typeclass use 1 - interface object
object JsonInterfaceObject {
  def toJson[T](value: T)(implicit w: JsonWritable[T]): Json = w.write(value)
}

// typeclass use 2 - interface syntax
object JsonInterfaceSyntax {
  implicit class JsonConverter[T](value: T) {
    def toJson(implicit w: JsonWritable[T]): Json = w.write(value)
  }
}

case class Cat(name: String, year: Double, wild: Boolean)

object Cat {
  implicit val jsonWriter: JsonWritable[Cat] = new JsonWritable[Cat] {
    override def write(cat: Cat): Json =
      JsObject(
        Map(
          "name" -> JsString(cat.name),
          "year" -> JsNumber(cat.year),
          "wild" -> JsBoolean(cat.wild)
        )
      )
  }
}

object MyJson extends App {

  val json: Json = JsObject(
    Map(
      "name" -> JsString("Sachin"),
      "age" -> JsNumber(30),
      "formats" -> JsArray(
        Seq(JsString("ODI"), JsString("TEST"), JsString("20x20"))
      ),
      "captain" -> JsBoolean(false)
    )
  )

  println(json)

  import JsonWritableObject._
  println(JsonInterfaceObject.toJson("My JSON String"))

  import JsonInterfaceSyntax._

  println("My custom syntax".toJson)

  println(Cat("Billy", 2020, false).toJson)
}
