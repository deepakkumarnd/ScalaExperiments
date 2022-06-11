import scala.annotation.tailrec

trait RNG {
  def nextInt: (Int, RNG)
}

class SimpleRNG(seed: Long) extends RNG {
  override def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = new SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }
}

val rng = new SimpleRNG(42)

rng.nextInt
rng.nextInt
val (n2, rng2) = rng.nextInt
rng2.nextInt
val (n3, rng3) = rng2.nextInt

rng3.nextInt

def randomPair(rng: RNG) = {
  val (n1, rng2) = rng.nextInt
  val (n2, rng3) = rng2.nextInt
  ((n1, n2), rng3)
}

randomPair(rng)

// Exercise 1

@tailrec
def nonNegativeInt(rng: RNG): (Int, RNG) = {
  val (n, rng2) = rng.nextInt
  if(n == Int.MinValue) nonNegativeInt(rng2)
  else if (n < 0) (-n, rng2)
  else (n, rng2)
}

nonNegativeInt(rng2)


def double(rng: RNG): (Double, RNG) = {
  val (n, rng2) = rng.nextInt
  if (n == 0) (0, rng2)
  else if (n == 1) double(rng2)
  else (1/n.toDouble, rng2)
}

double(rng)


// Exercise 2

def intDouble(rng: RNG): ((Int, Double), RNG) = {
  val (n2, rng2)  = rng.nextInt
  val (n3, rng3) = double(rng2)

  ((n2, n3), rng3)
}

intDouble(rng)


def doubleInt(rng: RNG): ((Double, Int), RNG) = {
  val (n2, rng2) = double(rng)
  val (n3, rng3) = rng2.nextInt
  ((n2, n3), rng3)
}

doubleInt(rng)

def double3(rng: RNG): ((Double, Double, Double), RNG) = {
  val (n1, rng1) = double(rng)
  val (n2, rng2) = double(rng1)
  val (n3, rng3) = double(rng2)

  ((n1, n2, n3), rng3)
}


// Exercise 3

def ints(count: Int)(rng: RNG): (List[Int], RNG) = {
  def loop(i: Int, acc: List[Int], rng1: RNG): (List[Int], RNG) = {
    if (i > 0) {
      val (n1, rng2) = rng1.nextInt
      loop(i - 1, n1 :: acc, rng2)
    } else (acc, rng1)
  }

  loop(count, List.empty[Int], rng)
}

ints(10)(rng)


type Rand[+A] = RNG => (A, RNG)

def unit[A](a: A): Rand[A] = rng => (a, rng)

def map[A, B](a: Rand[A])(f: A => B): Rand[B] = rng => {
  val (a1, rng1) = a(rng)
  (f(a1), rng1)
}

def nonNegativeEven: Rand[Int] = map(nonNegativeInt)(i => i - i % 2)

nonNegativeEven(rng)

def int: Rand[Int] = _.nextInt

def double: Rand[Double] = map(int)(i => i.toDouble)

double(rng)

def map2[A, B, C](a: Rand[A], b: Rand[B])(f: (A, B) => C): Rand[C] = rng => {
  val (a1, rng1) = a(rng)
  val (b1, rng2) = b(rng1)
  (f(a1, b1), rng2)
}

def both[A, B](a: Rand[A], b: Rand[B]): Rand[(A, B)] = map2(a, b)((_, _))

val randIntDouble: Rand[(Int, Double)] = both(int, double)
val randDoubleInt: Rand[(Double, Int)] = both(double, int)

def sequence[A](list: List[Rand[A]]): Rand[List[A]] = list match {
  case Nil => unit(Nil)
  case head :: tail => map2(head, sequence(tail))((x,y) => x :: y)
}


val lists = List.fill[Rand[Int]](5){ int }

sequence(lists)(rng)

def nonNegativeLessThan(n: Int): Rand[Int] = map(nonNegativeInt)(_ % n)

nonNegativeLessThan(10)(rng)
nonNegativeLessThan(20)(rng)
nonNegativeLessThan(20)(rng)

def flatMap[A, B](a: Rand[A])(f: A => Rand[B]): Rand[B] = rng => {
  val (a1, rng2) = a(rng)
  f(a1)(rng2)
}

def nonNegativeLessThanN(n: Int): Rand[Int] =
  flatMap(nonNegativeInt) { a =>
    val mod =  a % n
    if (a + (n - 1) - mod >= 0)
      unit(mod)
    else nonNegativeLessThanN(n)
  }

nonNegativeLessThanN(5)(rng)

// map using flatMap

def mapV2[A, B](a: Rand[A])(f: A => B): Rand[B] = flatMap(a)(x => unit(f(x)))

mapV2(unit(10))(_.toDouble)(rng)

// map2 using flatMap
def map2V2[A, B, C](a: Rand[A], b: Rand[B])(f: (A, B) => C): Rand[C] =
  flatMap(a){ a =>
    flatMap(b) { b =>
      unit(f(a, b))
    }
  }


map2V2(unit(10), unit(20))((_, _))(rng)
