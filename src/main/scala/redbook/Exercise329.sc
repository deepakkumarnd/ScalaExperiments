sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]

def fold[A, B](tree: Tree[A], zero: B)(branchF: (B, B) => B)(leafF: (A, B) => B): B = tree match {
  case Leaf(value) => leafF(value, zero)
  case Branch(left, right) => branchF(fold(left, zero)(branchF)(leafF), fold(right, zero)(branchF)(leafF))
}

val t = Branch(Branch(Leaf(1), Leaf(8)), Branch(Branch(Leaf(3), Leaf(5)), Leaf(4)))

def size[A](tree: Tree[A]): Int = fold(tree, 1)((l, r) => l + r)((a, b) => b)
def maximum(tree: Tree[Int]): Int = fold(tree, Int.MinValue)(_ max _)(_ max _)
def maxDepth[A](tree: Tree[A]): Int = fold(tree,0)((l, r) => (1 + l) max (1 + r))((_, b) => b)
def map[A, B](tree: Tree[A])(f: A => B): Tree[B] =
  fold(tree, _: Tree[B])(Branch(_, _))((a, z) => Leaf(f(a)))


size(t)
maximum(t)
maxDepth(t)
map(t)(_ * 2)
