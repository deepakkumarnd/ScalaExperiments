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

val a = List(1 to 100 : _*)

def length[A](list: List[A]): Int = foldRight(list, 0)((_, b) => 1 + b)

length(a)
