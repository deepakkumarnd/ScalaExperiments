sealed trait List[+A] {
  def dropWhile(p: A => Boolean): List[A]
}

object List {
  def apply[A](xs: A*): List[A] = if(xs.isEmpty) Nil else Cons(xs.head, apply(xs.tail: _*))

  def append[A](a1: List[A], a2: List[A]): List[A] = a1 match {
    case Nil => a2
    case Cons(x, xs) => Cons(x, append(xs, a2))
  }

  // dropwWhile as a object method

  def dropWhile1[A](list: List[A], f: A => Boolean): List[A] = list match {
    case Nil => Nil
    case Cons(x, xs) =>
      if (f(x)) xs else Cons(x, dropWhile1(xs, f))
  }

  def dropWhile2[A](list: List[A])(f: A => Boolean): List[A] = list match {
    case Nil => Nil
    case Cons(x, xs) =>
      if (f(x)) xs else Cons(x, dropWhile2(xs)(f))
  }
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


val b = List(8,9)

List.append(a, b)
// Using dropwhile1 with sinle argument list
// Here we have to provide type information explicitly compiler can't infer it
List.dropWhile1[Int](a, x => x % 2 == 0)
// or
List.dropWhile1(a, (x: Int) => x % 2 == 0)
// Using dropWhile2 with multiple argument list
// Using multiple argument list will help in better type inference
List.dropWhile2(a)(x => x % 2 == 0)
