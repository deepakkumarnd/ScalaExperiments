sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

def maxDepth[A](tree: Tree[A], level: Int = 0): Int = tree match {
  case Leaf(_) => level
  case Branch(left, right) => maxDepth(left, level + 1) max maxDepth(right, level + 1)
}

val t = Branch(Leaf(1), Branch(Leaf(2), Branch(Leaf(3), Leaf(4))))

maxDepth(t)
