sealed trait List[+A]

object List {
  def setHead[A](head: A, list: List[A]): List[A] = list match {
    case Nil => Cons(head, Nil)
    case Cons(_, tail) => Cons(head, tail)
  }

  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

val a = List(2,3)
List.setHead(1, a)
