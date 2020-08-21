package scalawithcats.monoid

import cats._
import cats.implicits._

case class Counter(value: Int)

object Main extends App {
  val a = Monoid[Int].combine(2, 3)
  println(a)

  val UnitCounter = Counter(1)

  val counterMonoid = Monoid.instance[Counter](
    UnitCounter,
    (a, b) => Counter(Monoid[Int].combine(a.value, b.value))
  )

  println(counterMonoid.combine(Counter(2), Counter(2)))
}
