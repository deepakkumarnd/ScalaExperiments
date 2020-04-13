package euler

import scala.annotation.tailrec

object problem5 extends App {

  /*
  2520 is the smallest number that can be divided by each of the numbers from 1 to 10 without any remainder.
  What is the smallest positive number that is evenly divisible by all of the numbers from 1 to 20?
*/
  def simpleFactors(num: Int): List[Int] = {
    def loop(n: Int, divisor: Int, acc: List[Int]): List[Int] = {
      if (n == 1) acc
      else if (n % divisor == 0) loop(n/divisor, 2, acc ++ List(divisor))
      else loop(n, divisor + 1, acc)
    }

    loop(num, 2, List())
  }

  def smallestDivisibleByRange(range: Range.Inclusive): Int = {
    @tailrec
    def compact(lists: List[List[Int]], acc: List[Int]): List[Int] = lists match {
      case List()  => acc
      case head :: tail => compact(tail, acc ++ head.diff(acc))
    }

    compact(range.map(p => simpleFactors(p)).toList, List()).product
  }

  val ans = smallestDivisibleByRange(1 to 20) // 232792560

  println(ans)
}
