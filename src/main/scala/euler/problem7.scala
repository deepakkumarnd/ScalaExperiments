package euler

import scala.annotation.tailrec

object problem7 extends App {

/*
  By listing the first six prime numbers: 2, 3, 5, 7, 11, and 13, we can see that the 6th prime is 13.
  What is the 10 001st prime number?
*/

  def nthPrime(n: Int): Long = {
    @tailrec
    def loop(current: Long, i: Int): Long =
      if(i == n) current - 2
      else if(problem3.isPrime(current)) loop(current + 2, i + 1)
      else loop(current + 2, i)

    loop(3, 1)
  }

  println(nthPrime(10001))  // 104743
}
