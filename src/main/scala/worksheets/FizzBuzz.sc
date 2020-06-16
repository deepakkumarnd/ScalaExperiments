def fizzBuzz(i: Int): String = {
  if ((i % 3== 0) && (i % 5== 0))  "FizzBuzz"
  else if (i % 3== 0) "Fizz"
  else if (i % 5== 0) "Buzz"
  else i.toString
}

fizzBuzz(10)
fizzBuzz(9)
fizzBuzz(11)
fizzBuzz(15)

// Exercise: Define a def flexibleFizzBuzz method that takes a String => Unit
// callback function as its argument, and allows the caller to decide what they
// want to do with the output. The caller can choose to ignore the output, println
// the output directly, or store the output in a previously- allocated array they
// already have handy.

def flexibleFizzBuzz(i: Int, f: String => Unit): Unit = {
  val result = fizzBuzz(i)
  f(result)
}

val f = (s: String) => if (s == "FizzBuzz") println("Game over") else println("Run")

flexibleFizzBuzz(15, f)

flexibleFizzBuzz(10, (s) => println(s))
