
def uncurry[A, B, C](f: A => B => C): (A, B) => C = (a, b) => f(a)(b)

val f: Int => Int => Int = (a) => (b) => a + b

uncurry(f)(1,2)
uncurry[Int, Int, Int]((a) => (b) => a * b)(2, 3)

