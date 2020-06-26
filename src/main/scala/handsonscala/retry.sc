// A retry mechanism iterative
def retry[T](max: Int)(fn: => T): T = {
  var tries = 0
  var result: Option[T] = None

  while(result.isEmpty) {
    try {
      result = Some(fn)
    } catch {
      case e: Throwable =>
        tries += 1
        if (tries > max) throw e
        else println(s"Failed attempt #$tries")
    }
  }

  result.get
}

@scala.annotation.tailrec
def retryRecursive[T](remainingTries: Int)(fn: => T): T = {
  try { fn }
  catch {
    case e: Throwable =>
      if (remainingTries > 0) {
        println(s"Retrying attempts remaining $remainingTries")
        retryRecursive(remainingTries - 1)(fn)
      } else throw e
  }
}

retry(3){
  println("This block completed successfully")
}

try {
  retry(3) {
    throw new RuntimeException("Something went wrong")
  }
} catch { case e: Throwable => "ignore" }

retryRecursive(3) {
  println("This block completed successfully")
}


retryRecursive(3) {
  println("This block completed successfully")
}

try {
  retryRecursive(3) {
    throw new RuntimeException("Something went wrong")
  }
} catch { case e: Throwable => "ignore" }
