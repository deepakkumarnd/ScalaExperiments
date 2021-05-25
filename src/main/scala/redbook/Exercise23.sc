//def partial[A,B,C](a: A, f: (A, B) => C): B  => C = b => f(a, b)
//
//partial[Int, Int, Int](1, (a, b) => a + b)(2)
//partial(1, (a: Int, b: Int) => a * b)(2)

// two argument function to single argument function
def curry[A, B, C](f: (A, B) => C): A => B => C = a => b => f(a,b)
curry((a: Int, b: Int) => a + b)(1)(2)
