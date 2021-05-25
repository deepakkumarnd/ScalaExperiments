import scala.annotation.tailrec

def isSorted[T](arr: Array[T], f: (T, T) => Boolean): Boolean = {
  @tailrec
  def loop(i: Int): Boolean = {
    if (i + 1 < arr.length)
      f(arr(i), arr(i + 1)) && loop(i + 1)
    else true
  }

  loop(0)
}


isSorted(Array.empty[Int], (a: Int, b: Int) => a < b)
isSorted[Int](Array(1), (a, b) => a < b)
isSorted[Int](Array(1,2), (a, b) => a < b)
isSorted[Int](Array(1,2,3), (a, b) => a < b)
isSorted[Int](Array(1,2,3,5), (a, b) => a < b)
isSorted[Int](Array(1,3,2,5), (a, b) => a < b)
isSorted[String](Array("one","two","three"), (a, b) => a < b)
