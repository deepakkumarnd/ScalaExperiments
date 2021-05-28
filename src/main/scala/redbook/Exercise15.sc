sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

def foldLeft[A, B](list: List[A], zero: B)(f: (B, A) => B): B = list match {
  case Nil => zero
  case Cons(x, xs) => foldLeft(xs, f(zero, x))(f)
}

def merge[A](l1: List[A], l2: List[A]): List[A] = l1 match {
  case Nil => l2
  case Cons(x, xs) => Cons(x, merge(xs, l2))
}

def append[A](l1: List[A], l2: List[A]): List[A] = {
  val reverseL1 = foldLeft(l1, Nil: List[A])((z, a) => Cons(a, z))
  foldLeft(reverseL1, l2)((z, a) => Cons(a, z))
}

def concatLists[A](lists: List[List[A]])(combine: (List[A], List[A]) => List[A]): List[A] = {
  foldLeft(lists, Nil: List[A])((b, a) => combine(b, a))
}

val a = List(1,2,3)
val b = List(4, 5)

merge(a, b)

concatLists(List(List(0,1,2), List(3,4), List(5)))(merge)
concatLists(List(List(0,1,2), List(3,4), List(5)))(append)
