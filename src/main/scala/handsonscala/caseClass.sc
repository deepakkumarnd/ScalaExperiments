/**
  * case classes give you the following features
  * 1. Equality using ==
  * 2. copy method
  * 3. toString implementation
  * 4. Easy object creation without using new
  */
case class Point(x: Int, y: Int)

val p = Point(2,3)
val p2 = Point(2,3)
val p3 = p.copy(x = 10)

println(p.toString)
println(p == p2)
print(p == p3)

