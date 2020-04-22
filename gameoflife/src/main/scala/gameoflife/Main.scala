package gameoflife

object Main extends App {
  val arenaStr =
    """
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 1 0 0 0 0
      |0 0 0 1 1 1 1 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |0 0 0 0 0 0 0 0 0 0
      |""".stripMargin.trim

  val arena: Seq[Seq[Boolean]] =
    arenaStr
      .split("\n")
      .map(line => line.split(" ").map { c => (c == "1") }.toVector)
      .toVector

  val world = ConwaysWorld(arena)
  ConwaysWorld.tick(world)
}
