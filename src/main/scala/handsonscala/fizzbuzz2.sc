// a function (printer) is passed to decide what to do with the result

def fizBuzz(i: Int, f: String => Unit): Unit = {
  val result =
    if ((i % 3 == 0) && (i % 5 == 0)) "FizzBuzz"
    else if (i % 5 == 0) "Fizz"
    else if (i % 3 == 0) "Buzz"
    else  i.toString

  f(result)
}

val printer = (s: String) => println(s)

(1 to 100).foreach(i => fizBuzz(i, printer))
