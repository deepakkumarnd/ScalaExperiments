package example

class Or[A, B]

object InfixTypes extends App {
  val x: Or[Int, String] = new Or[Int, String]
  // The above line can be written in infix form as
  val y: Int Or String = new Or[Int, String]
}
