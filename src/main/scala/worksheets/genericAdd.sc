// Implement a generic add function
// def add[T](a: T, b: T): T = ???
// Reference https://typelevel.org/blog/2013/07/07/generic-numeric-programming.html
final case class Complex[T](i: T, j: T)

trait Addable[T] { def plus(a: T, b: T): T }

object Addable {
  implicit object IntAdder extends Addable[Int] {
    def plus(a: Int, b: Int): Int = a + b
  }

  implicit object DoubleAdder extends Addable[Double] {
    def plus(a: Double, b: Double): Double = a + b
  }

  implicit def complexAdder[T](implicit adder: Addable[T]): Addable[Complex[T]] = new Addable[Complex[T]] {
    override def plus(a: Complex[T], b: Complex[T]): Complex[T] = Complex(add(a.i, b.i), add(a.j, b.j))
  }

  // This will add an operator +++ for any value of type T that is Addable
  implicit class AddableOps[T](a: T)(implicit adder: Addable[T]) {
    def +++(b: T): T = adder.plus(a, b)
  }

  def add[T](a: T, b: T)(implicit adder: Addable[T]) = adder.plus(a, b)
}

import Addable._

add(1, 2)
add(1.2, 2.4)
add(1.2, 2)


val c1 = Complex(1,2)
val c2 = Complex(1,2)
add(c1, c2)
// or
c1 +++ c2
1 +++ 2
1.2 +++ 3
