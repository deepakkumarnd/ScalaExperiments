/**
 * Given an m*n matrix, print all possible paths originating from (0,0) and ending at (m-1, n-1). The directions can be assumed to be only right (R) and down (D)
 * eg. 2*2
 * paths [RD, DR]
 */


def findPaths(m: Int, n: Int): Unit = {
  def traverse(i: Int, j: Int, path: String): Unit = {
    if ((i == m - 1) && (j == n - 1)) {
      println(path)
    }

    if (i < m) traverse(i + 1, j, path + "R")
    if (j < n) traverse(i, j + 1, path + "D")
  }

  traverse(0, 0, "")
}

findPaths(3, 3)
