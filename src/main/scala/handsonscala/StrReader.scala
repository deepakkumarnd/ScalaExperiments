package handsonscala

trait StrReader[T] {
  def parse(s: String): T
}

object StrParer {
  implicit object ParseInt extends StrReader[Int] {
    def parse(s: String): Int = s.toInt
  }

  implicit object ParseBoolean extends StrReader[Boolean] {
    def parse(s: String): Boolean = s.toBoolean
  }

  implicit object ParseString extends StrReader[String] {
    def parse(s: String): String = s
  }

  implicit def parseSeq[T](implicit parser: StrReader[T]): StrReader[Seq[T]] =
    new StrReader[Seq[T]] {
      def parse(s: String): Seq[T] = s match {
        case s"[$str]" => str.split(",").toSeq.map(parser.parse)
        case _         => Seq.empty[T]
      }
    }

  def parseFromString[T](s: String)(implicit parser: StrReader[T]): T =
    parser.parse(s)

  implicit class StringParser(s: String) {
    def parseString[T](implicit p: StrReader[T]): T = p.parse(s)
  }
}

object Demo extends App {
  import StrParer._

  println(parseFromString[Seq[Boolean]]("[true,false,true]"))
  println("[true,false,true]".parseString[Seq[Boolean]])
}
