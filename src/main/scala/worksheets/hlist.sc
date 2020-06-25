sealed trait HList

case object HNil extends HList
case class ::[H, T <: HList](head: H, tail: T)
