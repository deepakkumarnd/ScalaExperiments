def isValidSudoku(grid: Seq[Seq[Int]]): Boolean = {

  def isValidSeq(seq: Seq[Int]): Boolean = {
    val filteredSeq = seq.filter(p => p != 0 )
    (filteredSeq.distinct.length == filteredSeq.length) && filteredSeq.forall(n => n < 10 && n > -1)
  }

  def generateSquares(i: Int): Seq[Seq[Int]] = {
    Range(i, i+3).map( s => {
      (for {
        x <- Range(i, i + 3)
        y <- Range((s%3)*3, (s%3)*3 + 3)
      } yield grid(x)(y))
    })
  }

  Range(0, 9).forall(i => {
    val row = grid(i)
    val col = Range(0, 9).map(grid(i)(_))

    val squares: Seq[Seq[Int]] = if (i % 3 == 0)
      generateSquares(i)
    else
      Seq.empty[Seq[Int]]

    isValidSeq(row) && isValidSeq(col) && squares.forall(s => isValidSeq(s))
  })
}

// solved grid

val grid1 = Seq(
  Seq(5,3,4,6,7,8,9,1,2),
  Seq(6,7,2,1,9,5,3,4,8),
  Seq(1,9,8,3,4,2,5,6,7),
  Seq(8,5,9,7,6,1,4,2,3),
  Seq(4,2,6,8,5,3,7,9,1),
  Seq(7,1,3,9,2,4,8,5,6),
  Seq(9,6,1,5,3,7,2,8,4),
  Seq(2,8,7,4,1,9,6,3,5),
  Seq(3,4,5,2,8,6,1,7,9)
)

// unsolved grid

val grid2 = Seq(
  Seq(0,3,4,6,7,8,9,0,2),
  Seq(6,7,0,1,0,0,3,4,0),
  Seq(1,0,0,3,4,0,5,0,0),
  Seq(0,5,0,0,6,1,4,0,3),
  Seq(4,0,6,8,5,0,7,9,1),
  Seq(0,1,3,9,2,4,8,5,6),
  Seq(9,0,1,0,0,7,2,8,4),
  Seq(0,8,7,4,1,9,6,0,5),
  Seq(3,0,5,0,8,0,1,7,9)
)


// Invalid grid
val grid3 = Seq(
  Seq(0,3,4,6,3,8,9,0,2),
  Seq(6,7,0,1,0,0,3,4,0),
  Seq(1,0,0,3,4,0,5,0,0),
  Seq(0,5,0,0,6,1,4,0,3),
  Seq(4,0,6,8,5,0,7,9,1),
  Seq(0,1,3,9,2,4,8,5,6),
  Seq(9,0,1,0,0,7,2,8,4),
  Seq(0,8,7,4,1,9,6,0,5),
  Seq(3,0,5,0,8,0,1,7,9)
)

// Invalid grid
val grid4 = Seq(
  Seq(0,3,4,6,0,0,9,0,2),
  Seq(0,7,0,1,0,0,0,4,0),
  Seq(1,0,0,3,4,0,5,0,0),
  Seq(0,5,0,0,6,1,4,0,3),
  Seq(4,0,6,8,5,0,7,9,1),
  Seq(0,1,3,9,2,4,8,5,6),
  Seq(9,0,1,0,0,8,2,0,4),
  Seq(0,8,7,4,1,9,6,0,5),
  Seq(0,0,5,0,8,0,1,7,9)
)

isValidSudoku(grid1)
isValidSudoku(grid2)
isValidSudoku(grid3)
isValidSudoku(grid4)
