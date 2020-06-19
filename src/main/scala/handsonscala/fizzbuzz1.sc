// simple fizzbuzz program to print numbers 1 - 100

def fizBuzz(i: Int): String =
  if ((i % 3 == 0) && (i % 5 == 0)) "FizzBuzz"
  else if (i % 5 == 0) "Fizz"
  else if (i % 3 == 0) "Buzz"
  else  i.toString

(1 to 100).foreach(i => println(fizBuzz(i)))
