package gameoflife

import scala.annotation.tailrec

class ConwaysWorld(arena: Seq[Seq[Boolean]]) {
  private val offset =
    Seq((-1, -1), (0, -1), (1, -1), (1, 0), (1, 1), (0, 1), (-1, 1), (-1, 0))

  def printArena(): Unit = {
    val str = arena
      .map { row => row.map { col => if (col) "1" else "0" }.mkString(" | ") }
      .mkString("\n")

    println(str)
    println("\n")
    Thread.sleep(1000)
  }

  def didWorldEnd(): Boolean = {

    for (i <- arena.indices; j <- arena.indices) {
      if (arena(i)(j)) return false
    }

    true
  }

  def nextGeneration(): ConwaysWorld = {
    val size = arena.length
    new ConwaysWorld(Seq.tabulate(size) { i =>
      Seq.tabulate(size) { j => applyConwaysRules(i, j) }
    })
  }

  private def applyConwaysRules(i: Int, j: Int): Boolean = {
    val count = neighbourhoodCount(i, j)

    if (count == 2) arena(i)(j)
    else if (count == 3) true
    else false
  }

  private def neighbourhoodCount(i: Int, j: Int): Int = {
    offset
      .map { off => (off._1 + i, off._2 + j) }
      .filter(cord =>
        arena.indices.contains(cord._1) && arena.indices.contains(cord._2)
      )
      .count(cord => arena(cord._1)(cord._2))
  }
}

object ConwaysWorld {
  def apply(arena: Seq[Seq[Boolean]]): ConwaysWorld = new ConwaysWorld(arena)

  @tailrec
  def tick(world: ConwaysWorld): Unit = {
    world.printArena()
    if (!world.didWorldEnd()) {
      tick(world.nextGeneration())
    }
  }
}
