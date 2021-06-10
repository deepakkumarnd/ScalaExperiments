
package ammonite
package $file.src.main.scala.redbook
import _root_.ammonite.interp.api.InterpBridge.{
  value => interp
}
import _root_.ammonite.interp.api.InterpBridge.value.{
  exit
}
import _root_.ammonite.interp.api.IvyConstructor.{
  ArtifactIdExt,
  GroupIdExt
}
import _root_.ammonite.compiler.CompilerExtensions.{
  CompilerInterpAPIExtensions,
  CompilerReplAPIExtensions
}
import _root_.ammonite.runtime.tools.{
  browse,
  grep,
  time,
  tail
}
import _root_.ammonite.compiler.tools.{
  desugar,
  source
}
import _root_.mainargs.{
  arg,
  main
}
import _root_.ammonite.repl.tools.Util.{
  PathRead
}


object Exercise45{
/*<script>*/import scala.annotation.tailrec

def Try[A](a: => A): Option[A] = try {
  Some(a)
} catch {
  case e: Exception => None
}

def traverse[A, B](a: List[A])(f: A => Option[B]): Option[List[B]] = {
  @tailrec
  def loop(l: List[A], acc: List[B]): List[B] = l match {
    case Nil => acc
    case head :: tail => f(head) match {
      case None => loop(tail, acc)
      case Some(x) => loop(tail, acc :+ x)
    }
  }

  val result = loop(a, List.empty[B])
  if (result.isEmpty) None else Some(result)
}

/*<amm>*/val res_3 = /*</amm>*/traverse(List("1", "2", "ab", "3"))(x => Try(x.toInt))
/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "Exercise45"
  /*</generated>*/
}
