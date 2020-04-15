package euler

import common.Util.toDigits

/*

2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.

What is the sum of the digits of the number 21000?

 */

object problem16 extends App {
  val base = BigInt(2)
  val ans = toDigits(base.pow(1000)).sum
  println(ans) // 1366
}
