package euler

import scala.annotation.tailrec

object problem14 extends App {

/*
  The following iterative sequence is defined for the set of positive integers:

  n → n/2 (n is even)
  n → 3n + 1 (n is odd)

  Using the rule above and starting with 13, we generate the following sequence:

    13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1

  It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms.
  Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.

  Which starting number, under one million, produces the longest chain?

  NOTE: Once the chain starts the terms are allowed to go above one million.
*/

//  var cache: Map[Int, Vector[Int]] = Map()

  def evenFn(x: Long): Long = x / 2
  def oddFn(x: Long): Long = 3 * x + 1

  @tailrec
  def collatzChain(start: Long, chain: Vector[Long] = Vector()): Vector[Long] = {

    if (start < 1) throw new IllegalArgumentException("chain should start with a value > 1")

    lazy val evenResult = evenFn(start)
    lazy val oddResult  = oddFn(start)

    if (start == 1L) chain ++ Vector(1.toLong)
    else if (start % 2 == 0) collatzChain(evenResult, chain ++ Vector(start))
    else collatzChain(oddResult, chain ++ Vector(start))
  }

  def longestChain(limit: Int): Int = {
    @tailrec
    def loop(length: Int, start: Int = 1, longestChainStart: Int = 1): Int = {
      lazy val chain = collatzChain(start)
      lazy val newLength = chain.length

//      cache = cache + (start -> chain)

      if (start == limit) longestChainStart
      else if (newLength < length) loop(length, start + 1, longestChainStart)
      else loop(newLength, start + 1, start)
    }

    loop(1)
  }

  val ans = longestChain(1000000)
  println(ans) // 837799
//  println(collatzChain(837799L).length)
//  println(cache)
}
