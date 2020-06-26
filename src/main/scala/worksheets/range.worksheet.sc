// Simple range
1 to 10 map { n => n * n }
// Until
1 until 10 map { n => n * 2 }
// step
1 to 10 by 2 map { n => n * 2 }

Range(1, 20) map ( n => n*n )
Range.inclusive(1, 20) map ( n => n*n )
