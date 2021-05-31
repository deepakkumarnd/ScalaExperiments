import scala.annotation.tailrec

def sequence[A](list: List[Option[A]]): Option[List[A]] = {
  @tailrec
  def loop(l: List[Option[A]], acc: List[A]): List[A] = l match {
    case Nil => acc
    case None :: tail => loop(tail, acc)
    case Some(a) :: tail => loop(tail, acc :+ a)
  }

  val result = loop(list, List.empty[A])

  if (result.isEmpty) None else Some(result)
}


sequence(List(Some(1), Some(2), None, Some(3)))

