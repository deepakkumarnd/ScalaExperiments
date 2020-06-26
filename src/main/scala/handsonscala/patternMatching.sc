
// extracting from string
val date = "27-05-1989"

date match {
  case s"$day-$month-$year" => println(s"Day $day of year " + year)
  case _ => "Incorrect date format"
}

// Pattern match on case classes

case class User(name: String, age: Int)

val u = new User("Deepak", 30)

u match {
  case User(name, age) => println(s"$name is of age $age")
  case _ => println("No")
}

