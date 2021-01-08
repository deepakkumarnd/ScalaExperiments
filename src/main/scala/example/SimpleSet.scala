package example

import scala.annotation.tailrec

trait SimpleSet[A] extends (A => Boolean) {

  override def apply(elem: A): Boolean = contains(elem)

  def contains(elem: A): Boolean
  def +(elem: A): SimpleSet[A]
  def ++(anotherSet: SimpleSet[A]): SimpleSet[A]
  def map[B](mapF: A => B): SimpleSet[B]
  def flatMap[B](mapF: A => SimpleSet[B]): SimpleSet[B]
  def filter(predicate: A => Boolean): SimpleSet[A]
  def foreach(func: A => Unit): Unit
  def -(elem: A): SimpleSet[A]
  def --(anotherSet: SimpleSet[A]): SimpleSet[A]
  def &(anotherSet: SimpleSet[A]): SimpleSet[A]
}

object SimpleSet {
  def apply[A](values: A*): SimpleSet[A] = {
    @tailrec
    def buildSet(valSeq: Seq[A], acc: SimpleSet[A]): SimpleSet[A] =
      valSeq match {
        case Nil          => acc
        case head :: tail => buildSet(tail, acc + head)
      }

    buildSet(values.toList, new EmptySet[A])
  }
}

class EmptySet[A] extends SimpleSet[A] {
  override def contains(elem: A): Boolean = false

  override def +(elem: A): SimpleSet[A] = new NonEmptySet(elem, this)

  override def ++(anotherSet: SimpleSet[A]): SimpleSet[A] = anotherSet

  override def map[B](mapF: A => B): SimpleSet[B] = new EmptySet[B]

  override def flatMap[B](mapF: A => SimpleSet[B]): SimpleSet[B] =
    new EmptySet[B]

  override def filter(predicate: A => Boolean): SimpleSet[A] = this

  override def foreach(func: A => Unit): Unit = ()

  override def -(elem: A): SimpleSet[A] = this

  override def --(anotherSet: SimpleSet[A]): SimpleSet[A] = this

  override def &(anotherSet: SimpleSet[A]): SimpleSet[A] = this

}

class NonEmptySet[A](head: A, tail: SimpleSet[A]) extends SimpleSet[A] {
  override def contains(elem: A): Boolean =
    (head == elem) || tail.contains(elem)

  override def +(elem: A): SimpleSet[A] =
    if (contains(elem)) this
    else new NonEmptySet[A](elem, this)

  override def ++(anotherSet: SimpleSet[A]): SimpleSet[A] =
    tail ++ anotherSet + head

  override def map[B](mapF: A => B): SimpleSet[B] = tail.map(mapF) + mapF(head)

  override def flatMap[B](mapF: A => SimpleSet[B]): SimpleSet[B] =
    tail.flatMap(mapF) ++ mapF(head)

  override def filter(predicate: A => Boolean): SimpleSet[A] = {
    val filteredTail = tail.filter(predicate)
    if (predicate(head)) filteredTail + head else filteredTail
  }

  override def foreach(func: A => Unit): Unit = {
    func(head)
    tail.foreach(func)
  }

  override def -(elem: A): SimpleSet[A] =
    if (head == elem) tail else tail - elem + head

  override def &(anotherSet: SimpleSet[A]): SimpleSet[A] =
    filter(anotherSet)

  override def --(anotherSet: SimpleSet[A]): SimpleSet[A] =
    filter(x => !anotherSet(x))
}

object SimpleSetDemo extends App {
  val printF = (x: Int) => print(s" $x ")
  val s1 = SimpleSet(1, 2, 3)
  println("New set")
  s1.foreach(printF)
  println("\nAdd 2")
  (s1 + 2).foreach(printF)
  println("\nAdd 4  - s2")
  val s2 = s1 + 4
  s2.foreach(printF)
  println("\nMap to doubles")
  s2.map(x => 2 * x).foreach(printF)
  println("\nFlat map on s2")
  s2.flatMap(x => s2 + (x * x)).foreach(printF)
  println("\nFilter")
  s2.filter(x => x % 2 == 0).foreach(printF)
  println("\n Concat s1 & new set")
  s1 ++ SimpleSet(8, 9) foreach printF
  println("\n Remove an element from s1")
  (s1 - 1).foreach(printF)
  println("\n remove set(1,2, 4) from s1")
  s1 -- SimpleSet(1, 2, 4) foreach printF
  println("\n Intersect s1 and s2")
  s1 & s2 foreach printF
}
