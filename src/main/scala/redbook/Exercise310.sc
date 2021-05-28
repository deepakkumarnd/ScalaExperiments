import scala.annotation.tailrec

sealed trait List[+A]
object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


def foldRight[A, B](list: List[A], zero: B)(f: (A, B) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => f(x, foldRight(xs, zero)(f))
}

@tailrec
def foldLeft[A, B](list: List[A], zero: B)(f: (B, A) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => foldLeft(xs, f(zero, x))(f)
}

val a = List(1,2,3,4)
val b = List(1.0, 2.0, 3.0, 4.0)

def sum(list: List[Int]): Int = foldLeft(list, 0)(_ + _)
def product(list: List[Double]): Double = foldLeft(list, 1.0)(_ * _)
def length[A](list: List[A]): Int = foldLeft(list, 0)((a, _) => a + 1)

sum(a)
product(b)
length(a)

def concat1(list: List[String]): String = foldRight(list, "")(_ + _)
def concat2(list: List[String]): String = foldLeft(list, "")(_ + _)


val b = List("A", "B")

concat1(b)
concat2(b)
