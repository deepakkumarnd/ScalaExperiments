package example

class Counter {
  private var _count: Int = 0

  def count = _count
  def increment = _count = _count + 1
  def incrementBy(x: Int) = _count = _count + x

  def count_=(x: Int) = _count = x
}

object MutableUpdate extends App {
  val c = new Counter
  println(c.count)
  c.increment
  println(c.count)
  c.incrementBy(10)
  println(c.count)
  c.count = 100
  println(c.count)
}
