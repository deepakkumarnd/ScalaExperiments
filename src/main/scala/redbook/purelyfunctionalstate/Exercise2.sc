trait RNG {
  def nextInt: (Int, RNG)
}

case class State[S, +A](run: S => (A, S)) {
  def flatMap[B](f: A => State[S, B]): State[S, B] = State(s => {
    val (a, s1) = run(s)
    f(a).run(s1)
  })

  def map[B](f: A => B): State[S, B] = flatMap(a => State.unit(f(a)))

  def map2[B, C](b: State[S,B])(f: (A,B) => C): State[S, C] = flatMap { a =>
    b.flatMap { b =>
      State(s => (f(a,b), s))
    }
  }

  def get: State[S, S] = State(s => (s, s))
  def set(s: S): State[S, Unit] = State(_ => ((), s))

  def modify(f: S => S): State[S, Unit] = for {
    s <- get
    _ <- set(f(s))
  } yield ()
}

object State {
  def unit[S, A](a: A): State[S, A] = State(s => (a, s))
  def sequence[S, A](states: List[State[S, A]]): State[S, List[A]] =
    states.foldRight(unit[S, List[A]](List.empty[A])) {
      (a,b) => a.map2(b)(_ :: _)
    }
}

type Rand[A] = State[RNG, A]

class SimpleRNG(seed: Long) extends RNG {
  override def nextInt: (Int, RNG) = {
    val newSeed = (seed * 0x5DEECE66DL + 0xBL) & 0xFFFFFFFFFFFFL
    val nextRNG = new SimpleRNG(newSeed)
    val n = (newSeed >>> 16).toInt
    (n, nextRNG)
  }
}

val rng = new SimpleRNG(10)

State.unit(10)
State.unit(10).run(rng)

val list: List[State[RNG, Int]] = List(1,2,3,4).map(State.unit)
State.sequence[RNG, Int](list).run(rng)


sealed trait Input
case object Coin extends Input
case object Turn extends Input

case class Machine(locked: Boolean, candies: Int, coins: Int) {
  def coin: Machine =
    if (locked && (candies > 0))
      copy(locked = false, coins = coins + 1)
    else this

  def turn: Machine =
    if (!locked)
      copy(locked = true, candies = candies - 1)
    else this

  override def toString: String = s"Machine($locked, $candies, $coins)"
}

type StateMachine = State[Machine, Unit]

val machine1 = Machine(true, 5, 10)

assert(Machine(true, 5, 10).coin == Machine(false, 5, 11))
assert(Machine(false, 5, 11).turn == Machine(true, 4, 11))



def simulateMachine(state0: Machine, inputs: List[Input]) = {
  val s0: StateMachine = State.unit(())

//  val finalState = inputs.foldRight(s0) { (input, sm) =>
//    input match {
//      case Coin =>
//        sm.modify(m => m.coin)
//      case Turn =>
//        sm.modify(m => m.turn)
//    }
//  }

//  finalState.run(state0)._2

  val s1 = s0.modify(m => m.coin)
  val s2 = s0.modify(m => m.turn).run(s0.modify(m => m.coin).run(state0)._2)

  s2
}

simulateMachine(machine1, List(Coin, Turn))
