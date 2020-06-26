// How to implement the following method which can accept a string and parse
// to a particular type.

//def parseFromString[T](s: String): T = ???

val input = Seq("Deepak", "12", "true")

//parseFromString[String](input(0))
//parseFromString[Int](input(1))
//parseFromString[Boolean](input(2))

trait StrParser[T] {
  def parse(s: String): T
}

object ParseInt extends StrParser[Int] {
  def parse(s: String): Int = s.toInt
}

object ParseString extends StrParser[String] {
  def parse(s: String): String = s
}

object ParseBoolean extends StrParser[Boolean] {
  def parse(s: String): Boolean = s.toBoolean
}

ParseBoolean.parse(input(2))
// From the console directly
def parseFromConsole[T](parser: StrParser[T]): T = {
  println("Please provide an input:")
  parser.parse(scala.Console.in.readLine())
}

// Blocking run from a terminal
// val intVal = parseFromConsole[Int](ParseInt)
// println(s"Parsed input $intVal")

// Implementation 1
def parseFromString[T](s: String, parser: StrParser[T]): T = parser.parse(s)

parseFromString[Boolean](input(2), ParseBoolean)

// Implementation 2 using implicit
trait StrParser2[T] {
  def parse(s: String): T
}

object StrParser2 {

  implicit object ParseInt extends StrParser2[Int] {
    def parse(s: String): Int = s.toInt
  }

  implicit object ParseString extends StrParser2[String] {
    def parse(s: String): String = s
  }

  implicit object ParseBoolean extends StrParser2[Boolean] {
    def parse(s: String): Boolean = s.toBoolean
  }

  implicit def parseSeq[T](implicit parser: StrParser2[T]): StrParser2[Seq[T]] = new StrParser2[Seq[T]] {
    def parse(s: String): Seq[T] = s.split(",").toSeq.map(parser.parse)
  }

  implicit def parseTuple2[K, V](implicit parser1: StrParser2[K], parser2: StrParser2[V]): StrParser2[(K, V)] = new StrParser2[(K, V)] {
    def parse(s: String): (K, V) = {
      val parts = s.split("=")
      (parser1.parse(parts.head), parser2.parse(parts.tail.head))
    }
  }
}

def parseFromString2[T](s: String)(implicit parser: StrParser2[T]): T = parser.parse(s)

parseFromString2[String](input(0))
parseFromString2[Int](input(1))
parseFromString2[Boolean](input(2))

// another syntactic sugar for the parseFromString2 method is

def parseFromString3[T : StrParser2](s: String): T = implicitly[StrParser2[T]].parse(s)

parseFromString3[Boolean](input(2))

// Parsing sequences

parseFromString2[Seq[Int]]("1,2,3,4")
parseFromString2[(String, Int)]("age=20")

