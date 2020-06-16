

//  Format
//  for ( generators and filters ) yield expression
//  generator is of the form a <- e, e is an expression whole value is a collection
//  filter is of the form if e, e is an expression returning boolean
//  For multiline we can use {} instead of ()

// Usage 1: for loop
for (i <- 1 to 10) println(i)

// Usage 2: multiline
for ( i <- Range.inclusive(10, 100).by(10)) {
  val square = i * i
  println(square)
}

// Usage 3: multiline nested loops, with filters (guards)
for {
  i <- 1 to 3
  j <- 1 to 3
  if (i + j) > 0
  if (i > j)
} yield (i, j)

// Usage 4: Unwrapping Option
for ( i <- Some(10)) println(i * i)
