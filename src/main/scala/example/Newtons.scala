package example

import scala.annotation.tailrec

// Find square root of a number using newtons method

object Newtons extends App {

  def abs(a: Double): Double = if (a < 0) -a else a

  def sqrt(x: Double): Double = {

    def goodGuess(guess: Double): Boolean =
      abs(x - (guess * guess)) / x < 0.01

    def improve(guess: Double): Double = (guess + (x / guess)) / 2

    @tailrec
    def newtonsSqrt(guess: Double): Double = {
      if (goodGuess(guess)) guess else newtonsSqrt(improve(guess))
    }

    newtonsSqrt(1)
  }

  println(sqrt(2))
  println(sqrt(400))
  println(sqrt(0.0004))
  println(sqrt(0.00004))
  // Very large number
  println(sqrt(2e60))
  // Very small numbers
  println(sqrt(2e-6))
}
