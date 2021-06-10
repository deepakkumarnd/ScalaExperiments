sealed trait Stream[+A] {
  def headOption: Option[A]
  def toList: List[A]
}

object Stream {
  def cons[A](head: => A, tail: => Stream[A]): Stream[A] = {
    lazy val h = head
    lazy val t = tail
    Cons(() => h, () => t)
  }

  def empty[A]: Stream[A] = Empty

  def apply[A](xs: A*): Stream[A] =
    if (xs.isEmpty) empty[A] else cons(xs.head, apply(xs.tail: _*))
}

case object Empty extends Stream[Nothing] {
  override def headOption: Option[Nothing] = None
  override def toList: List[Nothing] = List.empty[Nothing]
}
case class Cons[+A](head: () => A, tail: () => Stream[A]) extends Stream[A] {
  override def headOption: Some[A] = Some(head())

  override def toList: List[A] = {
    def loop(stream: Stream[A], list: List[A]): List[A] = stream match {
      case Empty => list
      case Cons(x, xs) => loop(xs(), list :+ x())
    }

    loop(this, List.empty)
  }
}

def expensive: Int = {
  println("Computing")
  3
}

val s = Stream(1,2,3)

s.headOption

val s2 = Stream(expensive, expensive)

//s2.headOption

s.toList

s2.toList





