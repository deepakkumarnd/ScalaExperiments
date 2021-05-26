import scala.annotation.tailrec

sealed trait List[+A]
object List {
  def sum(list: List[Int]): Int = list match {
    case Nil              => 0
    case Cons(head, tail) => head + sum(tail)
  }

  def product(list: List[Double]): Double = list match {
    case Nil              => 1.0
    case Cons(0.0, _)     => 0.0
    case Cons(head, tail) => head * product(tail)
  }

  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil
    else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

val x = List(1,2,3,4,5) match {
  case Cons(x, Cons(2, Cons(4, _))) => x
  case Nil => 42
  case Cons(x, Cons(y, Cons(3, Cons(4, _)))) => x + y
  case Cons(h, t) => h + List.sum(t)
  case _ => 101
}


