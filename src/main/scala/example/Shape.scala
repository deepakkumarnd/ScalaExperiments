package example

import java.io.InvalidObjectException

object Shapes {

  final case class Point(x: Double, y: Double)

  private def square(x: Double): Double = x * x

  sealed trait Shape

  final case class Line(point1: Point, point2: Point) {
    def length: Double =
      math.sqrt(
        square(point2.x - point1.x) + square(point2.y - point1.y)
      )
  }

  final case class Rectangle(
      point1: Point,
      point2: Point,
      point3: Point,
      point4: Point
  ) extends Shape

  final case class Triangle(point1: Point, point2: Point, point3: Point)
      extends Shape

  final case class Circle(center: Point, circumPoint: Point) extends Shape

  final case class Polygon(vertices: Seq[Point]) extends Shape

  def area(shape: Shape): Double = shape match {
    case Circle(c, p) => math.Pi * square(Line(c, p).length)
    case Triangle(p1, p2, p3) =>
      val (s1, s2, s3) =
        (Line(p1, p2).length, Line(p2, p3).length, Line(p1, p3).length)
      val s = (s1 + s2 + s3) / 2
      math.sqrt(s * (s - s1) * (s - s2) * (s - s3))
    case Rectangle(p1, p2, p3, p4) =>
      area(Triangle(p1, p2, p3)) + area(Triangle(p1, p3, p4))
    case Polygon(vertices) =>
      // Shoelace formula
      val sum = vertices
        .zip(vertices.tail :+ vertices.head)
        .map({ case (p, q) => (p.x * q.y) - (p.y * q.x) })
        .sum
        .abs
      sum / 2
    case _ => throw new InvalidObjectException("Illegal shape")
  }
}

object ShapeDemo extends App {
  import Shapes._

  // Square
  println(
    area(Rectangle(Point(0, 0), Point(0, 20), Point(20, 20), Point(20, 0)))
  )
  println(
    area(Polygon(Seq(Point(0, 0), Point(0, 20), Point(20, 20), Point(20, 0))))
  )
  // Triangle
  println(area(Triangle(Point(0, 0), Point(0, 10), Point(10, 0))))
  println(area(Polygon(Seq(Point(0, 0), Point(0, 10), Point(10, 0)))))

  // Polygon area is incorrect when the side lines goes out of bounds
  println(
    area(
      Polygon(
        Seq(
          Point(0, 0),
          Point(0, 20),
          Point(20, 20),
          Point(20, 40),
          Point(40, 40),
          Point(40, 0)
        )
      )
    )
  )
}
