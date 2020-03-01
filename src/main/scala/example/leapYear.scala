package example

object leapYear extends App {

  def isLeapYear(year: Int): Boolean = {
    ((year % 4) == 0) && ((year % 100 == 0) || ((year % 400) == 0))
  }

  println(isLeapYear(Integer.parseInt("1996")))
  println(isLeapYear(Integer.parseInt("2000")))
  println(isLeapYear(Integer.parseInt("2004")))
}
