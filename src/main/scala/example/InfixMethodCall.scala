package example

class Number(val n: Int) {
  def isFactorOf(x: Int) = n % x == 0
}

/**
  * You can write method calls in infix form for single argument methods on caller
  */
object InfixMethodCall extends App {
  val num = new Number(100)
  val yes = num isFactorOf 10
  print(yes)
}
