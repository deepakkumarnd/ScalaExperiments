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

@scala.annotation.tailrec
def retryWithExponentialBackOff[T](max: Int, attempt: Int = 1)(fn: => T): T = {
  try { fn }
  catch {
    case e: Throwable =>
      if (attempt < max) {
        val delay = math.pow(2, attempt).toInt * 10 // 20, 40, 80 millis...
        println(s"Attempt #$attempt failed retrying after $delay milliseconds")
        Thread.sleep(delay)
        retryWithExponentialBackOff(max, attempt + 1)(fn)
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


try {
  retryWithExponentialBackOff(10) {
    throw new RuntimeException("Something went wrong")
  }
} catch { case e: Throwable => "ignore" }
