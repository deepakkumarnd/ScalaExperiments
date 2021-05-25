import scala.annotation.tailrec

def fib(n: Int): Int = {
  @tailrec
  def loop(i: Int, last: Int, secondLast: Int): Int = {
    if (i == n) last + secondLast
    else loop(i + 1, last + secondLast, last)
  }

  if (n == 1 || n == 2) 1
  else if (n > 2) loop(3, 1, 1)
  else -1
}

fib(0)
fib(1)
fib(2)
fib(3)
fib(4)
fib(5)
fib(6)


