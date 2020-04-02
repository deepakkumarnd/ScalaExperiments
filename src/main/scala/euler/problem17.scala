package euler

import util.{toDigits, power}

/*
  If the numbers 1 to 5 are written out in words: one, two, three, four, five,
  then there are 3 + 3 + 5 + 4 + 4 = 19 letters used in total.
  If all the numbers from 1 to 1000 (one thousand) inclusive were written out
  in words, how many letters would be used?

  NOTE: Do not count spaces or hyphens. For example, 342 (three hundred and
  forty-two) contains 23 letters and 115 (one hundred and fifteen) contains
  20 letters. The use of "and" when writing out numbers is in compliance with
  British usage.
 */

object problem17 extends App {

  val words: Map[Int, String] = Map(
    0 -> "",
    1 -> "one",
    2 -> "two",
    3 -> "three",
    4 -> "four",
    5 -> "five",
    6 -> "six",
    7 -> "seven",
    8 -> "eight",
    9 -> "nine",
    10 -> "ten",
    11 -> "eleven",
    12 -> "twelve",
    13 -> "thirteen",
    14 -> "fourteen",
    15 -> "fifteen",
    16 -> "sixteen",
    17 -> "seventeen",
    18 -> "eighteen",
    19 -> "nineteen",
    20 -> "twenty",
    30 -> "thirty",
    40 -> "forty",
    50 -> "fifty",
    60 -> "sixty",
    70 -> "seventy",
    80 -> "eighty",
    90 -> "ninety"
  )

  def numberToWords(number: Int): String = {
    def convert(num: Int): String = {
      if (!(0 to 1000).contains(num)) throw new IllegalArgumentException
      else if ((0 to 20).contains(num)) words(num)
      else if ((21 to 99).contains(num)) {
        val digits = toDigits(num)
        words(digits(0) * 10) + " " + words(digits(1))
      } else if ((100 to 999).contains(num)) {
        val digits = toDigits(num)
        words(digits(0)) + " hundred and " + convert(
          digits(1) * 10 + digits(2)
        )
      } else if (num == 1000) "one thousand"
      else "Unknown"
    }

    convert(number).trim.stripSuffix(" and")
  }

  val ans = (1 to 1000)
    .map(n => numberToWords(n))
    .map(word => word.count(p => ('a' to 'z').contains(p)))
    .sum

  println(ans) // 21124
}
