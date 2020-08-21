package scalawithcats.cats1

import java.util.Date

import cats.Show
import cats.instances.string._
import cats.instances.int._
import cats.syntax.show._

final case class Cat(name: String, age: Int, color: String)

object Cat {
//  implicit val showCat: Show[Cat] = new Show[Cat] {
//    override def show(t: Cat): String =
//      s"${t.name.show} is ${t.age.show} years old and is a ${t.color.show} cat."
//  }
//  OR

  implicit val showCat: Show[Cat] = Show.show(t =>
    s"${t.name.show} is ${t.age.show} years old and is a ${t.color.show} cat."
  )
}

object Main extends App {

  val cat = Cat("Billy", 12, "White")

  val showStr = Show.apply[String]
  val showInt = Show.apply[Int]

  println(showStr.show("Welcome to cats"))
  println(12.show)

  println(cat.show)

  implicit val dateShow: Show[Date] =
    Show.show(d => s"Day ${d.getDay} of month ${d.getMonth}")

  val date = new Date

  println(date.show)
}
