sealed trait Point

case class Point2D(x: Int, y: Int) extends Point
case class Point3D(x: Int, y: Int, z: Int) extends Point

def hypotenues(p: Point): Unit = p match {
  case Point2D(x, y) => math.sqrt(x*x + y *y)
  case Point3D(x, y, z) => math.sqrt(x*x + y*y + z*z)
  case _ => println("Not a point")
}


val p1 = Point2D(1,3)
val p2 = Point3D(1,3,4)

println(hypotenues(p1))
println(hypotenues(p2))
