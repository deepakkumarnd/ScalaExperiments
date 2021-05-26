sealed trait List[+A]

object List {
  def tail[A](list: List[A]): List[A] = list match {
    case Nil           => Nil
    case Cons(_, tail) => tail
  }

  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil
    else Cons(xs.head, apply(xs.tail: _*))
}
case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

List.tail(List(1,2,3,4,5))
