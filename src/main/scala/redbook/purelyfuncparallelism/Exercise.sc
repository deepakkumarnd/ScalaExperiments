import java.util.concurrent.{Callable, ExecutorService, Future, TimeUnit}

//trait Par[A] {
//  def run: A
//  def map2[B](a: Par[B])(f: (A, B) => A)
//  def fork: Par[A]
//}
//
//object Par {
//  def unit[A](a: A): Par[A] = ???
//  def lazyunit[A](a: A): Par[A] = Par.unit(a).fork
//}

type Par[A] = ExecutorService => Future[A]

def run[A](s: ExecutorService)(a: Par[A]): Future[A] = a(s)

object Par {
  def unit[A](a: A): Par[A] = (es: ExecutorService) => UnitFuture(a)

  private case class UnitFuture[A](get: A) extends Future[A] {
    override def cancel(mayInterruptIfRunning: Boolean) = false

    override def isCancelled = false

    override def isDone = true

    override def get(timeout: Long, unit: TimeUnit) = get
  }

  def map2[A,B,C](a: Par[A], b: Par[B])(f: (A,B) => C): Par[C] = (es: ExecutorService) => {
    val af = a(es)
    val bf = b(es)
    UnitFuture(f(af.get(), bf.get()))
  }

  def fork[A](a: Par[A]): Par[A] = es =>
    es.submit(new Callable[A] {
      override def call() = a(es).get()
    })

  def asyncF[A,B](f: A => B): A => Par[B] = a => unit(f(a))

  def map[A,B](a: Par[A])(f: A => B): Par[B] = map2(a, unit(()))((a, _) => f(a))

  def sortPar[A](parList: Par[List[Int]]): Par[List[Int]] = map(parList)(_.sorted)

  def sequence[A](ps: List[Par[A]]): Par[List[A]] =
    ps.foldRight(unit(List.empty[A])) { (a, b) => map2(a,b)(_ :: _) }

  def parMap[A, B](ps: List[A])(f: A => B): Par[List[B]] = {
    val fbs: List[Par[B]] = ps.map(asyncF(f))
    fork(sequence(fbs))
  }

  def parFilter[A](as: List[A])(f: A => Boolean): Par[List[A]] = ???
}


val list = List(1,2,3,4)

Par.parMap(list)(_ % 2 == 0)
