package example

// Implement && and || without using && and ||

object AndOr extends App {

  def and(a: Boolean, b: => Boolean) = if (a) b else false
  def or(a: Boolean, b: => Boolean) = if (a) true else b

  and(true, false)
  and(false, true)
}
