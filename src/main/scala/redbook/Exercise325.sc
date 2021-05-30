sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

def size[A](tree: Tree[A]): Int = tree match {
  case _: Leaf[A] => 1
  case Branch(left, right) => size(left) + size(right)
}

val t = Branch(Branch(Leaf(1), Leaf(2)), Leaf(3))

size(t)
