package example

// DSL Syntax
//  set field "foo" of bar to "baz"

class To {
  def to(value: AnyVal) = value
}

class Of {
  def of(obj: AnyRef) = new To()
}

object set {
  def field(name: String): Of = new Of()
}

class Bar(val name: String)

object DSLDemo extends App {

  val bar = new Bar("Test")

  set field "foo" of bar to "baz"

  println(bar.name)
}




