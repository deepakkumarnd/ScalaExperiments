package euler

import common.Util.factorize

object problem21 extends App {

  /*

  Let d(n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n).
    If d(a) = b and d(b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.

  For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

  Evaluate the sum of all the amicable numbers under 10000.

   */

  def amicablePair(num: Int): Option[(Int, Int)] = {
    val factorSum = factorize(num).dropRight(1).sum
    if (factorize(factorSum).dropRight(1).sum == num) Some(num, factorSum.toInt)
    else None
  }

  val ans = (1 until 10000)
    .map(num => amicablePair(num))
    .filter({
      case Some((a, b)) => !(a == b)
      case None         => false
    })
    .flatMap({ case Some(pair) => Seq(pair._1, pair._2) })
    .distinct
    .sum

  println(ans) // 31626
}
