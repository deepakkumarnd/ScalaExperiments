package scalawithcats

trait Printable[A] {
  def format(value: A): String
}

object PrintableInstances {
  implicit val intPrinter: Printable[Int] = new Printable[Int] {
    override def format(value: Int): String = value.toString
  }

  implicit val strPrinter: Printable[String] = new Printable[String] {
    override def format(value: String): String = value
  }
}

object Printable {
  def format[A](a: A)(implicit p: Printable[A]): String = p.format(a)
  def print[A](a: A)(implicit p: Printable[A]): Unit = println(p.format(a))
}

object PrintableSyntax {
  implicit class PrintableOp[A](a: A) {
    def format(implicit p: Printable[A]): String = p.format(a)
    def print(implicit p: Printable[A]): Unit = println(format)
  }
}

final case class Cat(name: String, age: Int, color: String)

object Cat {
  import PrintableInstances._
  implicit val catPrinter: Printable[Cat] = new Printable[Cat] {
    override def format(cat: Cat): String = {

      val name = Printable.format(cat.name)
      val age = Printable.format(cat.age)
      val color = Printable.format(cat.color)

      s"${name} is ${age} year old ${color} cat."
    }
  }
}

object Main extends App {
  val cat = Cat("Billy", 5, "Black")
  Printable.print(cat)

  import PrintableSyntax._

  cat.print
}
