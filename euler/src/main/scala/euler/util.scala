package euler

import scala.annotation.tailrec

object util {

  def isPrime(l: Long): Boolean = {
    @tailrec
    def loop(el: Long, limit: Long): Boolean =
      if (el > limit) true
      else if (l % el == 0) false
      else loop(el + 2L, l / (el + 2L))

    if (l == 2) true
    else if (l % 2L == 0) false
    else loop(3L, l / 3L)
  }

  def factorize(num: Long): List[Long] = {
    @tailrec
    def loop(el: Long, acc: Set[Long], limit: Long): List[Long] = {
      if (el > limit) {
        acc.toList.sorted
      } else if (num % el == 0L) {
        val quotient = num / el
        loop(el + 1L, acc ++ Set(el, quotient), quotient)
      } else {
        loop(el + 1L, acc, limit)
      }
    }

    loop(2L, Set(1, num), num / 2L)
  }

  def simpleFactors(num: Int): List[Int] = {
    @tailrec
    def loop(n: Int, divisor: Int, acc: List[Int]): List[Int] = {
      if (n == 1) acc
      else if (n % divisor == 0) loop(n/divisor, 2, acc ++ List(divisor))
      else loop(n, divisor + 1, acc)
    }

    loop(num, 2, List())
  }

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

  @tailrec
  def factorial(n: Int, acc: BigInt = BigInt(1)): BigInt = if (n > 1) factorial(n-1, n * acc) else acc

  @tailrec
  def toDigits(num: BigInt, acc: Seq[Int] = Seq.empty[Int]): Seq[Int] =
    if (num > 0) toDigits(num / 10, Seq((num % 10).toInt) ++ acc)
    else acc
}
