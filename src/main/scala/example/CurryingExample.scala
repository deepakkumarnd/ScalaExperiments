package example

object CurryingExample extends App {

  //  Write a product function that calculates product of numbers in an intervel

  def sum(a: Int, b: Int): Int = {
    if (a > b) 0 else a + sum(a + 1, b)
  }

  def product(a: Int, b: Int): Int = {
    if (a > b) 1 else a * product(a + 1, b)
  }

  def factorial(n: Int): Int = {
    product(1,n)
  }

  def doWhatever(fn: (Int, Int) => Int, a: Int, b: Int): Int = {
    fn(a, b)
  }

  def doWhateverPlus(fn: (Int, Int) => Int) = {
    fn
  }

  def mapReduce(map: Int => Int, reduce: (Int, Int) => Int, unit: Int, a: Int, b: Int): Int = {
    def process(a: Int, b: Int): Int = {
      if (a > b) {
        unit
      } else {
        reduce(map(a), process(a + 1, b))
      }
    }

    process(a,b)
  }

  println(product(1,3))
  println(factorial(3))

  println(sum(1,5))

  println(doWhatever(sum, 1,5))
  println(doWhatever(product, 1,5))

  println(doWhateverPlus(sum)(1,5))

  println(mapReduce(x => x, (x, y) => x + y, 0, 1,5))
  println(mapReduce(x => x, (x, y) => x * y, 1, 1,5))
}
