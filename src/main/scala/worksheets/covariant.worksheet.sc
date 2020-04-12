abstract class Food {
  def name: String
}

class Plate[+T <: Food](content: T) {
  override def toString: String = s"Plate of ${content.name}"
}


abstract class Veg extends Food

abstract class NonVeg extends Food


// Upper bound is set to Food
class Person[T <: Food] {
  def eat(f: T): Unit = println(s"I am eating ${f.name}")

  def eatFromPlate(plate: Plate[T]): Unit = println(s"Eating a plate of ${plate}")
}

class Biriyani(dishName: String) extends NonVeg {
  override def toString: String = "Biriyani"

  def name: String = dishName
}

class Dosa(dishName: String) extends Veg {
  override def toString: String = "Dosa"

  def name: String = dishName
}

val p1 = new Person[Veg]
val p2 = new Person[NonVeg]

val f1 = new Dosa("Masala Dosa")
val f2 = new Biriyani("Chicken Biriyani")

// A vegetarian person can be fed with Veg food
p1.eat(f1)
// A non-vegetarian person can be fed with NonVeg food
p2.eat(f2)

// plate of dosa
val plate1 = new Plate(f1)
// plate of biriyani
val plate2 = new Plate(f2)

p1.eatFromPlate(plate1)
p2.eatFromPlate(plate2)




