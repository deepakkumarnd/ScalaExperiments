package euler

object problem9 extends App {

/*
  A Pythagorean triplet is a set of three natural numbers, a < b < c, for which,
  a^2 + b^2 = c^2

  For example, 32 + 42 = 9 + 16 = 25 = 52.

  There exists exactly one Pythagorean triplet for which a + b + c = 1000.
  Find the product abc.
*/

  val triplet = for {
    i <- 1 to 1000
    j <- (i + 1) to 1000 - i
    k <- (j + 1) to 1000 - i - j

    if i + j + k == 1000
    if k*k == i*i + j*j
  } yield Vector(i,j,k)

  println(triplet)
  println(triplet(0).product) // 31875000
}
