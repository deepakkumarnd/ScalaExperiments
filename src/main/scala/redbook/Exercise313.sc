import scala.annotation.tailrec

sealed trait List[+A]
object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

@tailrec
def foldLeft[A, B](list: List[A], zero: B)(f: (B, A) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => foldLeft(xs, f(zero, x))(f)
}


def reverse[A](list: List[A]): List[A] = foldLeft(list, Nil: List[A])((b, a) => Cons(a, b))

// foldRight using foldLeft
def foldRight[A, B](list: List[A], zero: B)(f: (A, B) => B): B = foldLeft(reverse(list), zero)((b, a) => f(a, b))

val a = List("A", "B", "C", "D")

def concat(list: List[String]): String = foldRight(list, "")(_ + _)

concat(a)

