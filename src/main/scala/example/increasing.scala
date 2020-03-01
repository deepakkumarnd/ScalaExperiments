package example

// Learning pattern matching
// Given an array of numbers, determine whether the array is strictly increasing or strictly decreasing.

object increasing extends App {

  val increasingPattern = List(1,2,3,4)
  val decreasingPattern = List(4,3,2,1)
  val noOrderPattern = List(3,4,5,1)

  def loop(list: List[Int], order: String): String = list match {
    case List(_) => order
    case head::tail =>
      val newOrder = if (head < tail.head) "increasing" else "decreasing"

      if (newOrder != order) "noorder" else loop(tail, order)
  }

  def increasingOrDecreasing(list: List[Int]): String = list match {
    case List() => "Empty string"
    case List(_) => "Single element"
    case head::tail => if (head < tail.head) loop(tail, "increasing") else loop(tail, "decreasing")
  }

  println(increasingOrDecreasing(increasingPattern))
  println(increasingOrDecreasing(decreasingPattern))
  println(increasingOrDecreasing(noOrderPattern))
}
