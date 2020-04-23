package euler

import java.io.File

import scala.io.Source

object problem22 extends App {

  /*
  Using names.txt (right click and 'Save Link/Target As...'), a 46K text file containing over
  five-thousand first names, begin by sorting it into alphabetical order. Then working out the
  alphabetical value for each name, multiply this value by its alphabetical position in the list
  to obtain a name score.

   For example, when the list is sorted into alphabetical order, COLIN, which is worth
   3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list. So, COLIN would obtain a
   score of 938 × 53 = 49714.

   What is the total of all the name scores in the file?
   */

  case class Name(text: String, score: Int, pos: Int)

  def scoreOf(str: String): Int = str.map({ c => c - 'A' + 1 }).sum

  val data = Source.fromResource("p022_names.txt").mkString.replaceAll("\"", "")

  val names: IndexedSeq[String] = data.split(",").toIndexedSeq

  val nameSeq =
    names.sorted.zipWithIndex.map({
      case (str, index) =>
        Name(str, scoreOf(str), index + 1)
    })

  val ans = nameSeq.map(name => name.score * name.pos).sum

  println(ans) // 871198282
}
