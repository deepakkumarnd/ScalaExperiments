package handsonscala.algorithms

class Trie {
  class Node(
      var hasValue: Boolean,
      val childrens: collection.mutable.Map[Char, Node] =
        collection.mutable.Map.empty[Char, Node]
  )

  val root = new Node(false)

  def add(str: String): Unit = {
    var current = root
    for (c <- str)
      current = current.childrens.getOrElseUpdate(c, new Node(false))
    current.hasValue = true
  }

  def contains(str: String): Boolean = {
    var current = Option(root)
    for (c <- str) current = current.get.childrens.get(c)
    current.exists(_.hasValue)
  }

  def stringsMatchingPrefix(prefix: String): Set[String] = {
    val setBuilder = Set.newBuilder[String]
    var current = Option(root)

    for (c <- prefix if current.isDefined)
      current = current.get.childrens.get(c)

    def traverse(n: Node, chars: List[Char]): Unit = {
      if (n.hasValue) setBuilder.addOne(prefix + chars.reverse.mkString(""))
      n.childrens.foreach(t => traverse(t._2, t._1 :: chars))
    }

    current.foreach(traverse(_, Nil))
    setBuilder.result()
  }

  def prefixesMatchingString(str: String): Set[String] = {
    val setBuilder = Set.newBuilder[String]
    var current = Option(root)
    var chars: List[Char] = Nil

    for (c <- str if current.isDefined) {
      if (current.get.hasValue) {
        setBuilder.addOne(chars.mkString(""))
      }
      chars = chars :+ c
      current = current.get.childrens.get(c)
    }
    setBuilder.result()
  }
}

object Demo extends App {
  val t = new Trie

  t.add("man")
  t.add("mango")
  t.add("mandarin")
  t.add("margin")
  t.add("adder")

  println(t.contains("mang"))
  println(t.contains("mango"))

  println(t.stringsMatchingPrefix("man"))
  println(t.prefixesMatchingString("mangolino"))
}
