import scala.annotation.tailrec
// Sum of any series from mth element to nth element

def sum(startIndex: Int, endIndex: Int)(implicit elem: (Int) => Int): Int = {

  @tailrec
  def loop(index: Int, acc: Int): Int =
    if (index <= endIndex) loop(index + 1, acc + elem(index)) else acc

  loop(startIndex, 0)
}

// Sum of numbers from 1 to 10 using implicit
// Implicit is an advanced language feature and should not be used if not needed
implicit def identity(x: Int) = x

sum(1, 10)

// Sum of squares from 1 to 10

sum(1, 10)((x) => x * x)

// Sum of cubes

sum(1, 10)((x) => x * x * x)

