package euler

import common.Util.factorial

/*

  Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down,
  there are exactly 6 routes to the bottom right corner.

  How many such routes are there through a 20×20 grid?

 */

// Solution
// For a grid with r x r dimension there are exactly r right moves and r down moves to reach the bottom right vertex
// Therefore there are n = r + r moves in any path. The problem is to find all combination of left and right moves within
// the 40 moves which further boils down to arranging right or down moves within the n number of moves. Thus the problem
// is a simple combination problem.
// Total paths will be C(n,r)

object problem15 extends App {

  def numberOfCombinations(n: Int, r: Int): BigInt =
    (factorial(n) / factorial(n - r)) / factorial(r)

  def numberOfFullPaths(gridSize: Int): BigInt =
    numberOfCombinations(gridSize * 2, gridSize)

  val ans = numberOfFullPaths(20) // ans = 137846528820

  println(ans)
}
