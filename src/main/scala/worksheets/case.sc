// for a multiple type match, here Int and String, a super type should be used
// if there is no match then it will result in an error so it is better to have
// a _ match all remaining scenarios clause at the end

def todayIs(day: Any): String =  day match {
  case "sunday" | "saturday" | 1 | 7 => "Holiday"
  case "monday" | "tuesday" | "thursday" | "friday" | 2 | 3 | 5 | 6 => "Working day"
  case "wednesday" | 4 => "No meeting day"
  case d: Int if d > 7 && d < 31 => "Some day" // guard condition
  case _ => "Not a day"
}

todayIs("sunday")
todayIs("wednesday")
todayIs(1)
todayIs(7)
todayIs(20)
todayIs(40)
todayIs("fooday")
