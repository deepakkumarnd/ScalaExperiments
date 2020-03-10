// Usage as a class level variable or cache

class User {
  // constructor code runs everytime a new User is created
  User.increment
}

object User {
  var count = 0

  def increment = count = count + 1
}

val u1 = new User
val u2 = new User

User.count

//  Usage as a syntactic sugar for object creation
class Animal(name: String, sound: String) {
  def cry = println(sound)
}

object Animal {
  def apply(name: String, sound: String) = new Animal(name, sound)
}

// A new object can be created as follows using the above method
val dog = Animal("Dog", "Bow bow")
val cat = Animal("Cat", "Meauew meauew")

dog.cry
cat.cry