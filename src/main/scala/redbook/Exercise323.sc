sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

def zipWith[A, B, C](l1: List[A], l2: List[B])(f: (A, B) => C): List[C] = (l1, l2) match {
  case (Cons(x, t1), Cons(y, t2)) => Cons(f(x, y), zipWith(t1, t2)(f))
  case _ => Nil
}

val l1 = List(1,2,3)
val l2 = List("a", "b", "c")

zipWith(l1, l2)((_, _))

