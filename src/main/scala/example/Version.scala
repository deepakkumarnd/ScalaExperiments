package example

import scala.math.Ordered.orderingToOrdered

sealed trait Version {
  val major: Int
  val minor: Int
  val patch: Int
}

object Version {
  def parse: PartialFunction[String, Version] = {
    case s"a$major.$minor.$patch" =>
      AndroidVersion(major.toInt, minor.toInt, patch.toInt)
    case s"i$major.$minor.$patch" =>
      IosVersion(major.toInt, minor.toInt, patch.toInt)
  }

  implicit class ToString(version: Version) {
    def asString: String = {
      val prefix = version match {
        case v: AndroidVersion => "a"
        case v: IosVersion     => "i"
      }

      s"$prefix${version.major}.${version.minor}.${version.patch}"
    }

    def ===(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) == (that.major, that.minor, that.patch)
    }

    def !==(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) != (that.major, that.minor, that.patch)
    }

    def <=(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) <= (that.major, that.minor, that.patch)
    }

    def >=(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) >= (that.major, that.minor, that.patch)
    }

    def <(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) < (that.major, that.minor, that.patch)
    }

    def >(that: Version): Boolean = {
      if (version.getClass != that.getClass)
        throw new RuntimeException("Mismatched type comparison")
      (version.major, version.minor, version.patch) > (that.major, that.minor, that.patch)
    }
  }
}

final case class AndroidVersion(major: Int, minor: Int, patch: Int)
    extends Version
final case class IosVersion(major: Int, minor: Int, patch: Int) extends Version

object Main extends App {
  assert(
    Version.parse("a11.10.2") == AndroidVersion(11, 10, 2),
    "Andorid version parsing"
  )

  assert(
    Version.parse("i11.10.2") == IosVersion(11, 10, 2),
    "Ios version parsing"
  )

  assert(
    AndroidVersion(1, 2, 3).asString == "a1.2.3",
    "Android version to string"
  )

  assert(
    IosVersion(1, 2, 3).asString == "i1.2.3",
    "Android version to string"
  )

  assert(
    AndroidVersion(1, 2, 3) === AndroidVersion(1, 2, 3),
    "Android version comparison"
  )

  assert(
    AndroidVersion(1, 2, 300) < AndroidVersion(1, 3, 0),
    "Android version comparison"
  )

  assert(
    AndroidVersion(1, 2, 300) > AndroidVersion(1, 3, 0),
    "Android version comparison"
  )

  assert(
    AndroidVersion(1, 2, 3) === IosVersion(1, 2, 3),
    "Andorid and ios version comparison"
  )
}
