package example

object factorial extends App {

  def fact(n: Int): Int = {
    if (n < 1) 0
    else if (n == 1) 1
    else n * fact(n-1)
  }

  println(fact(10))
}
