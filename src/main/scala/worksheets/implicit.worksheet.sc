import java.time.temporal.WeekFields
// Implicit looks for implicit values in the current scope and use it by default

// Implicit parameters
implicit val name = "Deepak"

def printName(implicit name: String) = println(name)

// Here the name will be passed implicitly by the compiler
printName

// Automatic type conversion

val Days: Map[String, Int] = Map(
  "sunday" -> 1,
  "monday" -> 2,
  "tuesday" -> 3,
  "wednesday" -> 4,
  "thursday" -> 5,
  "friday" -> 6,
  "saturday" -> 7
)

case class WeekDay(day: String)

implicit def weekToInt(day: WeekDay): Int = Days(day.day)

// Here the type will be converted to Int automatically
new WeekDay("sunday") + 1

// Implicit classes can be used to add functionalities to existing types without modifying the code
// The String class has no method withoutVowels. This can be added like so:

object StringUtil {
  implicit class StringEnhancer(str: String) {
    def withoutVowels: String = str.replaceAll("[aeiou]", "")
  }
}

import StringUtil.StringEnhancer

"Education".withoutVowels
