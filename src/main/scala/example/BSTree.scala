package example

import Ordering.Implicits._
import scala.annotation.tailrec

case class BSTree[T: Ordering](
    left: Option[BSTree[T]],
    right: Option[BSTree[T]],
    value: T
) {
  def insert(value: T): BSTree[T] =
    BSTree.insert(this, value)

  def contains(value: T): Boolean =
    BSTree.contains(Some(this), value)

  def map(fn: T => T): BSTree[T] = {
    def mapF(bst: Option[BSTree[T]]): Option[BSTree[T]] = bst.map { subTree =>
      BSTree(
        left = mapF(subTree.left),
        right = mapF(subTree.right),
        value = fn(subTree.value)
      )
    }

    mapF(Some(this)).get
  }

  def delete(value: T): BSTree[T] =
    if (contains(value))
      BSTree.delete(Some(this), value).get
    else this

  override def toString(): String =
    value.toString + " " + left.getOrElse("").toString + right
      .getOrElse("")
      .toString
}

object BSTree {
  private def inOrderTraversal[T](
      subtree: Option[BSTree[T]],
      action: BSTree[T] => Unit
  ): Unit = subtree match {
    case Some(subTree) =>
      inOrderTraversal(subTree.left, action)
      action(subTree)
      inOrderTraversal(subTree.right, action)
    case None => ()
  }

  def traverse[T](
      bst: BSTree[T]
  )(action: BSTree[T] => Unit): Unit =
    inOrderTraversal(Some(bst), action)

  def insert[T: Ordering](bst: BSTree[T], value: T): BSTree[T] = {
    def insertValue(bst: Option[BSTree[T]]) = bst match {
      case Some(subTree) => Some(insert(subTree, value))
      case None          => Some(BSTree(None, None, value))
    }

    if (value < bst.value)
      bst.copy(left = insertValue(bst.left))
    else if (value > bst.value)
      bst.copy(right = insertValue(bst.right))
    else
      bst
  }

  def delete[T: Ordering](bst: Option[BSTree[T]], value: T): Option[BSTree[T]] =
    bst match {
      case None => None
      case Some(subTree) if value < subTree.value =>
        Some(subTree.copy(left = delete(subTree.left, value)))
      case Some(subTree) if value > subTree.value =>
        Some(subTree.copy(right = delete(subTree.right, value)))
      case Some(subTree) if value == subTree.value =>
        subTree match {
          case BSTree(Some(left), Some(right), _) =>
            val succ = inorderSuccessor(right)
            val tmpTree =
              BSTree(left = subTree.left, right = None, value = succ.value)

            Some(
              buildTree(
                tmpTree,
                subTree.right
              )
            )
          case BSTree(None, Some(right), _) => Some(right)
          case BSTree(Some(left), None, _)  => Some(left)
          case _                            => None
        }
    }

  private def buildTree[T](
      target: BSTree[T],
      source: Option[BSTree[T]]
  ): BSTree[T] = source match {
    case None => target
    case Some(tree) =>
      val newTarget = buildTree(target.insert(tree.value), tree.left)
      buildTree(newTarget.insert(tree.value), tree.right)
  }

  private def inorderSuccessor[T](bst: BSTree[T]): BSTree[T] = bst.left match {
    case None          => bst
    case Some(subTree) => inorderSuccessor(subTree)
  }

  @scala.annotation.tailrec
  def find[T: Ordering](bst: Option[BSTree[T]], value: T): Option[BSTree[T]] =
    bst match {
      case None => None
      case Some(subTree) =>
        if (value < subTree.value) find(subTree.left, value)
        else if (value > subTree.value) find(subTree.right, value)
        else Some(subTree)
    }

  def contains[T: Ordering](bst: Option[BSTree[T]], value: T): Boolean =
    find(bst, value) match {
      case None    => false
      case Some(_) => true
    }

  def fromSeq[T: Ordering](seq: Seq[T]): Option[BSTree[T]] = {
    def buildFromSeq(seq: Seq[T], acc: BSTree[T]): BSTree[T] =
      seq.headOption match {
        case Some(value) => buildFromSeq(seq.tail, acc.insert(value))
        case None        => acc
      }

    seq.headOption.map(value =>
      buildFromSeq(seq.tail, BSTree(None, None, value))
    )
  }
}

object BstTreeDemo extends App {

  val seq = Seq(11, 6, 8, 19, 4, 10, 5, 17, 43, 49, 31)

  val bst = BSTree.fromSeq(seq).get

  println(seq)
  println(bst)

  BSTree.traverse(bst) { tree => print(tree.value + " ") }
  println("")
  println(bst.contains(31))
  println(bst.contains(115))

  val newBst = bst.map { value => value * value }

  println(newBst)
  BSTree.traverse(newBst) { tree => print(tree.value + " ") }

  println("\n")

  val newTree = bst.delete(11).delete(31).delete(4)
  BSTree.traverse(newTree) { tree => print(tree.value + " ") }
}
