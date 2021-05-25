def compose[A, B, C](f: A => B, g: B => C): A => C = a => g(f(a))

compose((a: Int) => a.toString, (b: String) => b.length)(100)
