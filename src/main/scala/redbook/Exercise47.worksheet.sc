def sequence[E, A](list: List[Either[E, A]]): Either[E, List[A]] = {
  def loop(l: List[Either[E, A]], acc: List[A]): Either[E, List[A]] = l match {
    case Nil                  => Right(acc)
    case Left(value) :: tail  => loop(tail, acc)
    case Right(value) :: tail => loop(tail, acc :+ value)
  }

  loop(list, Nil)
}


sequence(List(Right(1), Right(2)))
sequence(List(Left("failed"), Right(1)))