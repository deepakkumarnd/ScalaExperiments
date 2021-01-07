package example

trait Action {
  def perform(num: Int): String
}

/*
 * Single abstract method traits/abstract class can be instatiated using a lambda as below
 */
object SingleAbstractMethodTrait extends App {
  val numToString: Action = (x: Int) => x.toString
  val numSqToString: Action = (x: Int) => (x * x).toString

  println(numToString.perform(10))
  println(numSqToString.perform(10))
}
