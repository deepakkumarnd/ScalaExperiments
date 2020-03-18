package euler

object problem10 extends App {
/*
  The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
  Find the sum of all the primes below two million.
*/

  def sumOfPrimes(below: Int): Long = {
    val primes = for {
      i <- 3 until below by 2
      if problem3.isPrime(i)
    } yield i.toLong

    primes.sum + 2L
  }

  val ans = sumOfPrimes(2000000)

  println(ans) // 142913828922

}
