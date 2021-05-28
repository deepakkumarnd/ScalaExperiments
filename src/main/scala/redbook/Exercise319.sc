sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


def filter[A](list: List[A])(p: A => Boolean): List[A] = list match {
  case Nil => Nil
  case Cons(x, xs) => if (p(x)) Cons(x, filter(xs)(p)) else filter(xs)(p)
}


filter(List(1,2,3,4,5))( _ % 2 == 0)
