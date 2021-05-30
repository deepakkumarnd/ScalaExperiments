sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

def maximum(tree: Tree[Int], maxValue: Int = Int.MinValue): Int = tree match {
  case Leaf(value) => maxValue.max(value)
  case Branch(left, right) => maximum(left) max maximum(right)
}

val t = Branch(Branch(Leaf(1), Leaf(2)), Branch(Leaf(8), Leaf(3)))

maximum(t)
