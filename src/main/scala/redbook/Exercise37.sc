sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] = if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

// How similar are sum and and product definitions ?
def sum(l: List[Int]): Int = l match {
  case Nil => 0
  case Cons(x, xs) => x + sum(xs)
}

def product(l: List[Double]): Double = l match {
  case Nil => 1
  case Cons(x, xs) => x * product(xs)
}

val a1 = List(1,2,3,4,5)
val a2 = List(1.0,2.0,3.0,4.0,5.0)

sum(a1)
product(a2)

// Here these two are using two types, different operations + and *, and different empty values
// other than that both the definitions are same, so if we take out the differences we can
// generalise the definitions as below.

def foldRight[A, B](l: List[A], zero: B)(f: (A, B) => B): B = l match {
  case Nil => zero
  case Cons(x, xs) => f(x, foldRight(xs, zero)(f))
}

def sum2(l: List[Int]): Int = foldRight(l, 0)((x, y) => x + y)
def product2(l: List[Double]): Double = foldRight(l, 1.0)(_ * _)

sum2(a1)
product2(a2)


// Short circuit might not be possible to generalise in foldRight

// calling with Nil and Cons will return the same a1
foldRight(a1, Nil: List[Int])(Cons(_, _))
