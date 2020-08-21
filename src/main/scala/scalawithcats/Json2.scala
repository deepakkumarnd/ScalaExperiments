package scalawithcats.json2

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

// implicit scope
object JsonWritable {
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

  implicit def seqWriter[T](implicit w: JsonWritable[T]): JsonWritable[Seq[T]] =
    new JsonWritable[Seq[T]] {
      override def write(value: Seq[T]): Json = JsArray(value.map(w.write(_)))
    }

  implicit def optWriter[T](
      implicit w: JsonWritable[T]
  ): JsonWritable[Option[T]] = new JsonWritable[Option[T]] {
    override def write(value: Option[T]): Json = value match {
      case None    => JsNull
      case Some(v) => w.write(v)
    }
  }

  // typeclass use 1 - interface object
  object Json {
    def toJson[T](value: T)(implicit w: JsonWritable[T]): Json = w.write(value)
  }

  // typeclass use 2 - interface syntax
  object syntax {
    implicit class JsonConverter[T](value: T) {
      def toJson(implicit w: JsonWritable[T]): Json = w.write(value)
    }
  }
}

case class Cat(name: String, year: Double, wild: Boolean)

object Cat {
  import JsonWritable.syntax._
  implicit val jsonWriter: JsonWritable[Cat] = new JsonWritable[Cat] {
    override def write(cat: Cat): Json =
      JsObject(
        Map(
          "name" -> cat.name.toJson,
          "year" -> cat.year.toJson,
          "wild" -> cat.wild.toJson
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

  import JsonWritable.Json
  import JsonWritable.syntax._

  println(Json.toJson("My JSON String"))

  println("My custom syntax".toJson)

  println(Cat("Billy", 2020, false).toJson)

  println(Seq("Billy", "Remo").toJson)
  println(Option(true).toJson)
  println(Option(Seq("OK")).toJson)
  println(Seq(Option("Ok")).toJson)
}
