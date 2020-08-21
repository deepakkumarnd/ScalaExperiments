import shapeless.Witness

class NonEmptyString[N <: Int](value: String) {
  override def toString(): String = value
}

object NonEmptyString {

}

val a = new NonEmptyString[100]("Ok This is my string")
a
