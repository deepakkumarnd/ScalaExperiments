sealed trait Tree[+A]

case class Leaf[A](value: A) extends Tree[A]
case class Branch[A](left: Tree[A], right: Tree[A]) extends Tree[A]


def map[A,B](tree: Tree[A])(f: A => B): Tree[B] = tree match {
  case Leaf(value) => Leaf(f(value))
  case Branch(left, right) => Branch(map(left)(f), map(right)(f))
}

val t = Branch(Leaf(1), Branch(Leaf(2), Branch(Leaf(3), Leaf(4))))

map(t)(_ * 2)
