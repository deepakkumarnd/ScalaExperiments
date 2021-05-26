sealed trait List[+A] {
  def dropWhile(p: A => Boolean): List[A]
}

object List {
  def apply[A](xs: A*): List[A] = if(xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))
}

case object Nil extends List[Nothing] {
  override def dropWhile(p: Nothing => Boolean): Nil.type = Nil
}

case class Cons[+A](head: A, tail: List[A]) extends List[A] {
  override def dropWhile(p: A => Boolean): List[A] = {
    if (p(head)) tail.dropWhile(p) else Cons(head, tail.dropWhile(p))
  }
}

val a = List(1,2,3,4,5)

a.dropWhile((x) => x % 2 == 0)

