package example

object gcd extends App {

  def gcdOf(a: Int, b: Int): Int = {
    if (a == 0) b
    else gcdOf(b % a, a)
  }

  println(gcdOf(4, 20))
  println(gcdOf(2, 5))
  println(gcdOf(8, 12))
}
