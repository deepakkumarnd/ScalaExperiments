package handsonscala.algorithms

object MergeSort extends App {
  def mergeSort[T](list: Seq[T])(implicit n: Ordering[T]): Seq[T] = {

    @scala.annotation.tailrec
    def merge(left: Seq[T], right: Seq[T], acc: Seq[T]): Seq[T] =
      (left, right) match {
        case (Nil, Nil) => acc
        case (_, Nil)   => acc ++ left
        case (Nil, _)   => acc ++ right
        case _ =>
          if (Ordering[T].lt(left.head, right.head))
            merge(left.tail, right, acc :+ left.head)
          else merge(left, right.tail, acc :+ right.head)
      }

    if (list.size <= 1) list
    else {
      val (left, right) = list.splitAt(list.length / 2)
      val (sortedLeft, sortedRight) = (mergeSort(left), mergeSort(right))
      merge(sortedLeft, sortedRight, Seq.empty)
    }
  }

  println(mergeSort(Seq(4, 0, 1, 5, 2, 3))) // Int
  println(mergeSort(Seq(4, 0, 1, 5, 2.4, 3.2))) // Double
  println(mergeSort(Seq("Deepak", "Amrutha"))) // Double

}
