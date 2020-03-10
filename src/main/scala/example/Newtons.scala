package example

// Find square root of a number using newtons method

object Newtons extends App {

  def abs(a: Double): Double = if (a < 0) -a else a

  def sqrt(x: Double): Double = {

    val offset: Double = if (x < 1) x / 1000 else 0.001

    def goodGuess(guess: Double): Boolean =
      abs(x - (guess * guess)) < offset

    def improve(guess: Double): Double = (guess + (x / guess)) / 2

    def newtonsSqrt(guess: Double): Double = {
      if (goodGuess(guess)) guess else newtonsSqrt(improve(guess))
    }

    newtonsSqrt(1)
  }

  println(sqrt(2))
  println(sqrt(400))
  println(sqrt(0.0004))
  println(sqrt(0.00004))
}
