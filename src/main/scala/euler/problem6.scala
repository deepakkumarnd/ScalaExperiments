package euler

import scala.annotation.tailrec

object problem6 extends App {
/*
  The sum of the squares of the first ten natural numbers is 1^2+2^2+...+10^2=385
  The square of the sum of the first ten natural numbers is (1+2+...+10)^2=552=3025

  Hence the difference between the sum of the squares of the first ten natural numbers
  and the square of the sum is 3025−385=2640. Find the difference between the sum of the
  squares of the first one hundred natural numbers and the square of the sum.
*/

  def sum(n: Int)(fn: Int => Int): Long = {
    @tailrec
    def loop(i: Int, acc: Long): Long = if(i > 0) loop(i - 1, fn(i) + acc) else acc
    loop(n, 0)
  }

  val sumOfSquares = sum(100)((x: Int) => x * x)
  val sums = sum(100)((x: Int) => x)
  val squareOfSum = sums * sums

  println(squareOfSum - sumOfSquares)
}
