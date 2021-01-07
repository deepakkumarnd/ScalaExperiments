package example

class Dog(val name: String, val age: Int)

object Dog {
  def unapply(dog: Dog): Option[(String, Int)] = Some((dog.name, dog.age))
}

object UnapplyMethod extends App {
  val a = new Dog("Jacky", 5)

  a match {
    case Dog(name, age) => println(s"Dog $name is $age years old")
  }

  val num = 8

  object evenNumber {
    def unapply(x: Int): Boolean = x % 2 == 0
  }

  object singleDigitNumber {
    def unapply(x: Int): Boolean = x < 10 && x > -10
  }

  num match {
    case evenNumber()        => println(s"$num is even")
    case singleDigitNumber() => println(s"$num is single digit number")
  }

  val l = Seq(1, 2, 3, 4)

  l match {
    case Seq(1, 2, _*) => println("Yes starts with 1 an 2")
  }
}
