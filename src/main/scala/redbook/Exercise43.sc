trait Option[+A] {
  def map[B](f: A => B): Option[B]
  def flatMap[B](f: A => Option[B]): Option[B]
  def getOrElse[B >: A](default: => B): B
  def orElse[B >: A](op: => Option[B]): Option[B]
  def filter(p: A => Boolean): Option[A]
}

case object None extends Option[Nothing] {
  override def map[B](f: Nothing => B): Option[B] = None

  override def flatMap[B](f: Nothing => Option[B]): Option[B] = None

  override def getOrElse[B >: Nothing](default: => B): B = default

  override def orElse[B >: Nothing](op: => Option[B]): Option[B] = op

  override def filter(p: Nothing => Boolean): Option[Nothing] = None
}

case class Some[A](value: A) extends Option[A] {
  override def map[B](f: A => B): Option[B] = Some(f(value))

  override def flatMap[B](f: A => Option[B]): Option[B] = f(value)

  override def getOrElse[B >: A](default: => B): B = value

  override def orElse[B >: A](op: => Option[B]): Option[B] = Some(value)

  override def filter(p: A => Boolean): Option[A] =
    if (p(value)) Some(value) else None
}


def map2[A, B, C](a: Option[A], b: Option[B])(f: (A, B) => C): Option[C] =
  a.flatMap { a =>
    b.map { b =>
      f(a, b)
    }
  }


def map3[A, B, C, D](a: Option[A], b: Option[B], c: Option[C])(f: (A, B, C) => D): Option[D] =
  a.flatMap { a =>
    b.flatMap { b =>
      c.map { c =>
        f(a, b, c)
      }
    }
  }