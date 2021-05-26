sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))

  def tail[A](xs: List[A]): List[A] = xs match {
    case Nil              => Nil
    case Cons(head, tail) => tail
  }

  def drop[A](n: Int, xs: List[A]): List[A] = {
    if (n > 0) drop(n - 1, tail(xs)) else xs
  }
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]


val a = List(1,2,3,4)

List.drop(2, a)




