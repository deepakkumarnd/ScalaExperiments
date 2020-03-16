package euler

import scala.annotation.tailrec

object problem3 extends App {
/*
  The prime factors of 13195 are 5, 7, 13 and 29.
  What is the largest prime factor of the number 600851475143 ?
*/
  def factorize(num: Long): List[Long] = {
    @tailrec
    def loop(el: Long, acc: List[Long], limit: Long): List[Long] = {
      if (el > limit) {
        acc
      } else if (num % el == 0L) {
        val quotient = num / el
        loop(el + 1L, acc ++ List(el, quotient).distinct, quotient)
      } else {
        loop(el + 1L, acc, limit)
      }
    }

    loop(2L, List(), num / 2L)
  }

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

  def primeFactors(num: Long): Seq[Long] = {

    def filterNonPrimes(list: List[Long]): List[Long] = for {
      el <- list
      if isPrime(el)
    } yield el

    if (num < 2) List()
    else if (num == 2 || num == 3) List(num)
    else filterNonPrimes(factorize(num)).sorted(Ordering[Long].reverse)
  }


  // val factors = primeFactors(600851475143L)
  val factors = primeFactors(600851475143L)

  factors match {
    case List() => println("No prime factors found")
    case _ => println(s"Highest prime factor is ${factors.head}") // 6857
  }
}
