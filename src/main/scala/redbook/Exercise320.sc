sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

def merge[A](l1: List[A], l2: List[A]): List[A] = l1 match {
  case Nil => l2
  case Cons(x, xs) => Cons(x, merge(xs, l2))
}

def flatMap[A, B](list: List[A])(f: A => List[B]): List[B] = list match {
  case Nil => Nil
  case Cons(x, xs) => merge(f(x), flatMap(xs)(f))
}


val a = List(1,2,3)

flatMap(a)((x) => List(x, x + 1, x + 2))
