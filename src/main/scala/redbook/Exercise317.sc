sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


def transform(list: List[Double]): List[String] = list match {
  case Nil => Nil
  case Cons(x, xs) => Cons(x.toString, transform(xs))
}

transform(List(1.0, 2.0, 3.0))
