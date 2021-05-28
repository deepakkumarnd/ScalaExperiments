sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


def transform(list: List[Int]): List[Int] = list match {
  case Nil => Nil
  case Cons(x, xs) => Cons(x + 1, transform(xs))
}

val a = List(1,2,3,4)

transform(a)
