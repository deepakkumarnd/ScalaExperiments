package handsonscala

trait StrWriter[T] {
  def write(data: T): String
}

object StrWriter {

  implicit object writeBoolean extends StrWriter[Boolean] {
    override def write(data: Boolean): String = data.toString
  }

  implicit def seqWriter[T](implicit writer: StrWriter[T]): StrWriter[Seq[T]] =
    new StrWriter[Seq[T]] {
      override def write(data: Seq[T]): String =
        "[" + data.map(_.toString).mkString(",") + "]"
    }

  def stringify[T](data: T)(implicit writer: StrWriter[T]): String =
    writer.write(data)
}

object DemoWrites extends App {
  import StrWriter._

  println(stringify(true))
  println(stringify(Seq(true, false, true)))
}
