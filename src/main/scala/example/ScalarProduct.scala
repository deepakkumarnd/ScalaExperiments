package example

object ScalarProduct extends App {

  val seq1 = 2 to 10 by 2
  val seq2 = 1 to 9 by 2

  def scalarProduct(seq1: Range, seq2: Range): List[Int] = for ((x,y) <- seq1.toList zip seq2.toList) yield x * y

  println(scalarProduct(seq1, seq2))
}
