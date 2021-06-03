sealed trait Either[+E, +A] {
  def map[B](f: A => B): Either[E, B]
  def flatMap[EE >: E, B](f: A => Either[EE, B]): Either[EE, B]
  def orElse[EE >: E, B >: A](other: Either[EE, B]): Either[EE, B]
  def map2[EE >: E, B, C](other: Either[EE, B])(
      f: (A, B) => C
  ): Either[EE, C]
}

case class Right[+A](value: A) extends Either[Nothing, A] {
  override def map[B](f: A => B): Either[Nothing, B] = Right(f(value))

  override def flatMap[E, B](f: A => Either[E, B]): Either[E, B] =
    f(value)

  override def orElse[EE >: Nothing, B >: A](other: Either[EE, B]) = this

  override def map2[EE >: Nothing, B, C](other: Either[EE, B])(f: (A, B) => C) =
    this.flatMap(a => other.map( b => f(a, b)))
}

case class Left[+E](value: E) extends Either[E, Nothing] {
  override def map[B >: Nothing](f: Nothing => B): Either[E, Nothing] = this

  override def flatMap[EE >: E, B >: Nothing](
      f: Nothing => Either[EE, B]
  ): Either[EE, Nothing] = this

  override def orElse[EE >: E, B](other: Either[EE, B]): Either[EE, B] = other

  override def map2[EE >: E, B, C](other: Either[EE, B])(f: (Nothing, B) => C) =
    this.flatMap(a => other.map( b => f(a, b)))
}

sealed trait MilkProducts {
  val name: String
  def asMilk = Milk(name)
}
case class Milk(name: String) extends MilkProducts {
  def toCurd: Curd = Curd(name)
  def toButter: Butter = Butter(name)
}
case class Curd(name: String) extends MilkProducts
case class Butter(name: String) extends MilkProducts

val milk: Milk = Milk("a")
val result: Either[Nothing, Milk] = Right(milk).map(_.toCurd).map(_.asMilk)

Right(10).map(_ => Left("10"))
Right(10).flatMap(_ => Left("10"))

Left("Error").orElse(Right(10))
Left("Error").orElse(Left(10))
Right("Something").orElse(Left(10))
Right("Something").orElse(Right(10))

val e1: Either[String, Int] = Left("Nothing1")
val e2: Either[String, Int] = Left("Nothing2")
val e3: Either[String, Int] = Right(10)
e1.map2(e2)(_ + _)
e3.map2(e1)(_ + _)
e3.map2(e3)(_ + _)
