package euler

import scala.annotation.tailrec

object problem4 extends App {
  /*

  A palindromic number reads the same both ways. The largest palindrome made from the
  product of two 2-digit numbers is 9009 = 91 × 99.

  Find the largest palindrome made from the product of two 3-digit numbers.
   */

  @tailrec
  def power(base: Int, pow: Int, acc: Int = 1): Int =
    if (pow > 0) power(base, pow - 1, acc * base) else acc

  def reverse(num: Int): Int = {
    @tailrec
    def loop(n: Int, acc: Int): Int =
      if (n > 0) loop(n / 10, (acc * 10) + (n % 10)) else acc

    loop(num, 0)
  }

  def isPalindrome(num: Int): Boolean = reverse(num) == num

  def largestPalindromeProduct(numberOfDigits: Int): Int = {
    val min = power(10, numberOfDigits - 1)
    val max = power(10, numberOfDigits) - 1

    val minLimit = min * min
    val maxLimit = max * max

    def hasFactorsWithDigts(num: Int): Boolean = {
      val factors = problem3.factorize(num)
      val nDigitFactors = factors.filter(p => (min to max).contains(p))

      nDigitFactors
        .flatMap(p => nDigitFactors.map(q => (p, q)))
        .map(p => p._1 * p._2)
        .contains(num)
    }

    def loop(n: Int): Int =
      if (n < minLimit) -1
      else if (isPalindrome(n) && hasFactorsWithDigts(n)) n
      else loop(n - 1)

    loop(maxLimit)
  }

  val ans = largestPalindromeProduct(3)

  println(ans) // 906609
}
