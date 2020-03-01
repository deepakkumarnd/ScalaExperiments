package example

// Learn
// 1. how to read input
// 2. how to interpolate variables in a string

object TwoForOne extends App {
  print("Input a name >> ")
  val line = scala.io.StdIn.readLine().strip()

  if (line.isEmpty) println("One for me and one for you")
  else println(s"One for $line and one for me")
}
