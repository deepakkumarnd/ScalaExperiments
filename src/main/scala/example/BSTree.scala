package example

import Ordering.Implicits._

case class BSTree[T: Ordering](
    left: Option[BSTree[T]],
    right: Option[BSTree[T]],
    value: T
) {
  def insert(value: T): BSTree[T] =
    BSTree.insert(this, value)

  def contains(value: T): Boolean =
    BSTree.contains(Some(this), value)

  def delete(value: T): BSTree[T] =
    if (contains(value))
      BSTree.delete(Some(this), value).get
    else this
}

object BSTree {
  private def inOrderTraversal[T](
      subtree: Option[BSTree[T]],
      action: BSTree[T] => Unit
  ): Unit = {
    subtree.map { tree =>
      inOrderTraversal(tree.left, action)
      action(tree)
      inOrderTraversal(tree.right, action)
    }
  }

  def traverse[T](
      bst: BSTree[T]
  )(action: BSTree[T] => Unit): Unit =
    inOrderTraversal(Some(bst), action)

  def insert[T: Ordering](bst: BSTree[T], value: T): BSTree[T] = {
    if (value < bst.value) {
      val newLeft = bst.left
        .map(subTree => insert(subTree, value))
        .orElse(Some(BSTree(None, None, value)))

      bst.copy(left = newLeft)
    } else if (value > bst.value) {
      val newRight = bst.right
        .map(subTree => insert(subTree, value))
        .orElse(Some(BSTree(None, None, value)))

      bst.copy(right = newRight)
    } else {
      bst
    }
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
}

object BstTree extends App {
  val bst = BSTree(None, None, 11)
    .insert(6)
    .insert(8)
    .insert(19)
    .insert(4)
    .insert(10)
    .insert(5)
    .insert(17)
    .insert(43)
    .insert(49)
    .insert(31)

  BSTree.traverse(bst) { tree => print(tree.value + " ") }
  println("")
  println(bst.contains(31))
  println(bst.contains(115))

  val newTree = bst.delete(11).delete(31).delete(4)
  BSTree.traverse(newTree) { tree => print(tree.value + " ") }
}
