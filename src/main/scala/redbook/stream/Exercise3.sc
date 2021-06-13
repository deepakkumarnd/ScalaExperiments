sealed trait Stream[+A] {
  def headOption: Option[A]
  def take(n: Int): Stream[A]
  def drop(n: Int): Stream[A]
  def toList: List[A]
  def takeWhile(p: A => Boolean): Stream[A]
  def exists(p: A => Boolean): Boolean
  def foldRight[B](z: => B)(f: (A, => B) => B): B
  def forAll(p: A => Boolean): Boolean
  def map[B](f: A => B): Stream[B]
  def filter(f: A => Boolean): Stream[A]
  def append[B >: A](b: => Stream[B]): Stream[B]
  def flatMap[B](f: A => Stream[B]): Stream[B]
  def find(p: A => Boolean): Option[A]
}

object Stream {
  def empty[A]: Stream[A] = Empty
  def cons[A](head: => A, tail: => Stream[A]): Stream[A] = {
    lazy val h = head
    lazy val t = tail
    Cons(() => h, () => t)
  }

  def apply[A](xs: A*): Stream[A] = {
    if (xs.isEmpty) Empty else cons(xs.head, apply(xs.tail: _*))
  }
}

case object Empty extends Stream[Nothing] {
  override def take(n: Int): Stream[Nothing] = Empty

  override def drop(n: Int): Stream[Nothing] = Empty

  override def toList: List[Nothing] = List.empty

  override def takeWhile(p: Nothing => Boolean) = Empty

  override def exists(p: Nothing => Boolean) = false

  override def foldRight[B](z: => B)(f: (Nothing, => B) => B): B = z

  override def headOption = None

  override def forAll(p: Nothing => Boolean): Boolean = false

  override def map[B](f: Nothing => B) = Stream.empty[B]

  override def filter(f: Nothing => Boolean): Stream[Nothing] = Stream.empty

  override def append[B >: Nothing](b: => Stream[B]): Stream[B] = b

  override def flatMap[B](f: Nothing => Stream[B]): Stream[B] = Stream.empty[B]

  override def find(p: Nothing => Boolean): Option[Nothing] = None
}

case class Cons[+A](head: () => A, tail: () => Stream[A]) extends Stream[A] {
  override def take(n: Int): Stream[A] = {
    if (n > 0) Stream.cons(this.head(), this.tail().take(n - 1))
    else Empty
  }

  override def drop(n: Int): Stream[A] = {
    if (n > 0) tail().drop(n - 1)
    else this
  }

  override def toList: List[A] = List(head()) ++ tail().toList

  override def takeWhile(p: A => Boolean): Stream[A] =
    if (p(head()))
      Stream.cons(head(), tail().takeWhile(p))
    else tail().takeWhile(p)

  override def exists(p: A => Boolean): Boolean = p(head()) || tail().exists(p)

  override def foldRight[B](z: => B)(f: (A, => B) => B): B =
    f(head(), tail().foldRight(z)(f))

  override def headOption = Some(head())

  override def forAll(p: A => Boolean): Boolean =
    foldRight(true)((a, b) => p(a) && b)

  override def map[B](f: A => B): Stream[B] =
    Stream.cons(f(head()), tail().map(f))

  override def filter(f: A => Boolean): Stream[A] =
    if (f(head())) Stream.cons(head(), tail().filter(f)) else tail().filter(f)

  override def append[B >: A](b: => Stream[B]): Stream[B] =
    Stream.cons(head(), tail().append(b))

  override def flatMap[B](f: A => Stream[B]): Stream[B] =
    f(head()).append(tail().flatMap(f))

  override def find(p: A => Boolean): Option[A] = filter(p).headOption
}


def constant[A](a: A): Stream[A] = Stream.cons(a, constant(a))

constant(1).take(2).toList

def from(i: Int): Stream[Int] = Stream.cons(i, from(i + 1))

from(10).take(10).toList

def fibs: Stream[Int] = {
  def loop(x: Int, y: Int): Stream[Int] = Stream.cons(x, loop(y, x + y))
  loop(1, 1)
}

fibs.take(10).toList


def unfold[A, S](z: S)(f: S => Option[(A, S)]): Stream[A] =
  f(z) match {
    case Some((a, z)) => Stream.cons(a, unfold(z)(f))
    case None => Stream.empty[A]
  }


unfold(1)(_ => Some(1, 1)).take(10).toList
unfold(1)(z => Some(z, z + 1)).take(10).toList
unfold((1, 1))(z => Some(z._1, (z._2, z._1 + z._2))).take(10).toList

def map[A,B](stream: Stream[A])(f: A => B): Stream[B] = {
  unfold(stream) {
    case Cons(head, tail) => Some(f(head()), tail())
    case Empty => None
  }
}

map(constant(1))(_ + 1).take(10).toList

def take[A](stream: Stream[A], n: Int): Stream[A] = {
  unfold((n, stream)) {
    case (_, Empty) => None
    case (0, _) => None
    case (i, Cons(head, tail)) => Some(head(), (i - 1, tail()))
  }
}

take(constant(1), 10).toList

// I did not understand this implementation, this is what is given as solution
// in many websites and in github. This does not take elements from stream if there is
// a en element which does not produce a true for the give predicate.
def takeWhile[A](stream: Stream[A])(p: A => Boolean): Stream[A] = {
  unfold(stream) {
    case Cons(head, tail) if p(head()) => Some((head(), tail()))
    case _ => None
  }
}


from(1).take(10).toList
takeWhile(from(2))(_ % 2 == 0).take(10).toList
