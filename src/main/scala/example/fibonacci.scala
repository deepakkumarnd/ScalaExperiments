package example

object fibonacci extends App {

  def fib(n: Int): Int = {
    if (n == 1) 0
    else if (n == 2) 1
    else fib(n - 1) + fib(n - 2)
  }

  println(fib(1))
  println(fib(2))
  println(fib(3))
  println(fib(4))
  println(fib(10))
}
