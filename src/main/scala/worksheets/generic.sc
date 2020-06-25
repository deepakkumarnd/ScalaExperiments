def identifyType[A](value:A) = {

  value match {
    case i : Int => println("Integer type")
    case d : Double => println("Double type")
    case f : Float => println("Float type")
    case s : String => println("String type")
    case _ : BigDecimal => println("Big decimal type")
    case _ => println("Unknown type")
  }

}

identifyType(10)
identifyType(10.0)
identifyType("OK")
