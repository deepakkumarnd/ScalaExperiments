import scala.annotation.tailrec

@tailrec
def gcd(a: Int, b: Int): Int = if (a == 0) b else gcd(b % a, a)

gcd(4, 20)
gcd(2, 5)
gcd(8, 12)

