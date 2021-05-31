import scala.annotation.tailrec

def Try[A](a: => A): Option[A] = try {
  Some(a)
} catch {
  case e: Exception => None
}

def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
  @tailrec
  def loop(l: List[A], acc: List[B]): List[B] = l match {
    case Nil => acc
    case head :: tail => f(head) match {
      case None => loop(tail, acc)
      case Some(x) => loop(tail, acc :+ x)
    }
  }

  val result = loop(a, List.empty[B])
  if (result.isEmpty) None else Some(result)
}

traverse(List("1", "2", "ab", "3"))(x => Try(x.toInt))
