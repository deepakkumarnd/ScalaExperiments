

//  Format
//  for ( generators and filters ) yield expression
//  generator is of the form a <- e, e is an expression whole value is a collection
//  filter is of the form if e, e is an expression returning boolean
//  For multiline we can use {} instead of ()

for (
  i <- 1 to 3; j <- 1 to i
  if (i + j) > 0
) yield (i, j)