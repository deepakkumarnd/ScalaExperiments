
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


object Exercise47{
/*<script>*/def sequence[E, A](list: List[Either[E, A]]): Either[E, List[A]] = {
  def loop(l: List[Either[E, A]], acc: List[A]): Either[E, List[A]] = l match {
    case Nil                  => Right(acc)
    case Left(value) :: tail  => loop(tail, acc)
    case Right(value) :: tail => loop(tail, acc :+ value)
  }

  loop(list, Nil)
}


/*<amm>*/val res_1 = /*</amm>*/sequence(List(Right(1), Right(2)))/*</script>*/ /*<generated>*/
def $main() = { scala.Iterator[String]() }
  override def toString = "Exercise47"
  /*</generated>*/
}
