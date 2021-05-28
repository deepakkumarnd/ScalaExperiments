import scala.annotation.tailrec

sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


@tailrec
def foldLeft[A, B](list: List[A], zero: B)(f: (B, A) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => foldLeft(xs, f(zero, x))(f)
}

def foldRight[A,B](list: List[A], zero: B)(f: (A,B) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => f(x, foldRight(xs, zero)(f))
}


def append1[A](l1: List[A], l2: List[A]): List[A] = foldRight(l1, l2)((a, z) => Cons(a, z))
def append2[A](l1: List[A], l2: List[A]): List[A] = foldLeft(l2, l1)((z, a) => Cons(a, z))

val a = List(1,2,3)
val b = List(4,5)

append1(a, b)
append2(a, b)
