def traverse[E,A,B](list: Seq[A])(f: A => Either[E, B]): Either[E, Seq[B]] = {

  def loop(l: Seq[A], acc: Seq[B]): Either[E, Seq[B]] = l match {
    case Nil => Right(acc)
    case head :: tail =>
      f(head) match {
        case Left(_) => loop(tail, acc)
        case Right(value) => loop(tail, acc :+ value)
      }
  }

  loop(list, Nil)
}

traverse(Seq(1,2,3,4))(x => if (x % 2 == 0) Left(x) else Right(x))

def traverseF[E,A,B](list: Seq[A])(f: A => Either[E, B]): Either[Seq[E], Nothing] = {
  def validate(l: Seq[A], acc: Seq[E]): Either[Seq[E], Nothing] = l match {
    case Nil => Left(acc)
    case head :: tail =>
      f(head) match {
        case Left(err) => validate(tail, acc :+ err)
        case Right(_) => validate(tail, acc)
      }
  }

  validate(list, Nil)
}

traverseF(Seq(1,2,3,4))(x => if (x % 2 == 0) Left(x) else Right(x))
traverseF(Seq(2,4))(x => if (x % 2 == 0) Left(x) else Right(x))
traverseF(Seq(1,3))(x => if (x % 2 == 0) Left(x) else Right(x))