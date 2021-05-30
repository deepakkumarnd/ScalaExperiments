sealed trait List[+A]

object List {
  def apply[A](xs: A*): List[A] =
    if (xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing]
case class Cons[+A](head: A, tail: List[A]) extends List[A]

def hasSubSequence[A](sup: List[A], sub: List[A]): Boolean =  {
  def findSequence(l1: List[A], l2: List[A]): Boolean = (l1, l2) match {
    case (Nil, Nil) => true
    case (Nil, _) => false
    case (_, Nil) => true
    case (Cons(a, t1), Cons(b, t2)) => if (a == b) findSequence(t1, t2) else findSequence(t1, sub)
  }

  findSequence(sup, sub)
}

val a = List(1,2,3,4)

hasSubSequence(a, List(0, 1))
hasSubSequence(a, List(1,2))
hasSubSequence(a, List(2,3))
hasSubSequence(a, List(4))
hasSubSequence(a, List(4,5))
hasSubSequence(a, List(5))
hasSubSequence(a, List(5, 6))
hasSubSequence(a, Nil)



