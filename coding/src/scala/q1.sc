import scala.annotation.tailrec
import scala.collection.immutable.Queue

/**
 * Given an m*n matrix, print all possible paths originating from (0,0) and ending at (m-1, n-1). The directions can be assumed to be only right (R) and down (D)
 * eg. 2*2
 * paths [RD, DR]
 */


def findPathsV1(m: Int, n: Int): Unit = {
  def traverse(i: Int, j: Int, path: String): Unit = {
    if ((i == m - 1) && (j == n - 1)) {
      println(path)
    }

    if (i < m) traverse(i + 1, j, path + "R")
    if (j < n) traverse(i, j + 1, path + "D")
  }

  traverse(0, 0, "")
}

/**
 * More efficient method
 */
def findPathV2(m: Int, n: Int): Unit = {
  @tailrec
  def traverse(queue: Queue[(Int, Int, String)], solutions: Seq[String]): Unit = {
    if (queue.isEmpty)
      solutions.foreach(println)
    else {
        val (head, tail) = queue.dequeue
        val (x, y, path) = head

        if ((x == m - 1) && (y == n -1)) traverse(tail, solutions :+ path)
        else if ((x > m) || (y > n)) traverse(tail, solutions)
        else traverse(tail ++ Queue((x + 1, y, path + "R"), (x, y + 1, path + "D")), solutions)
    }
  }

  traverse(Queue((0, 0, "")), Seq.empty[String])
}

//findPathsV1(3, 3)
findPathV2(3,3)
