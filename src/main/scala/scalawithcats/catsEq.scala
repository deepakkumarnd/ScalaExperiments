package scalawithcats.catseq

import cats.Eq
import cats.instances.string._
import cats.instances.int._
import cats.syntax.eq._

final case class Cat(name: String, age: Int, color: String)

object Cat {
  implicit val equality: Eq[Cat] = Eq.instance[Cat] { (c1, c2) =>
    (c1.name === c2.name) && (c1.age === c2.age) && (c1.color === c2.color)
  }
}

object Main extends App {
  val c1 = Cat("Billy", 3, "Black")
  val c2 = Cat("Billy", 3, "Black")

  val catEq = Eq.apply[Cat]

  println(c1 === c2)
}
