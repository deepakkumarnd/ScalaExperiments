package euler

object problem1 extends App {
  //  If we list all the natural numbers below 10 that are multiples of 3 or 5,
  //  we get 3, 5, 6 and 9. The sum of these multiples is 23.

  //  Find the sum of all the multiples of 3 or 5 below 1000.

  def sumOfMultiples(numbers: List[Int], lessThan: Int): Int =
    numbers.flatMap(x => multiplesOf(x, lessThan)).sum - multiplesOf(numbers.product, lessThan).sum

  def multiplesOf(n: Int, lessThan: Int): Range = n until lessThan by n

  val ans = sumOfMultiples(List(3,5), 1000)

  println(ans) // 233168
}
