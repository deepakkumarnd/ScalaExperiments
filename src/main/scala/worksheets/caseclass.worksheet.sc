// Case classes are special classes in scala, they are used to model data objects in general. 
// They are implemented as normal class with a companion object, the compiler puts the boiler-
// plate code to make the life easier for us.
// One major benefit with case class is that we can check the equality of two objects very easily.


case class Person(name: String, age: Int)

val p1 = Person("Deepak", 30)
val p2 = Person("Amrutha", 26)

// Data comparison
p1 == p1
// Reference comparison
p1 equals p2
p1 equals p1