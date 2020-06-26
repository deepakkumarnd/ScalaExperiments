sealed trait Expr

case class BinExpr(left: Expr, op: String, right: Expr) extends Expr
case class Literal(value: Int) extends Expr
case class Variable(name: String) extends Expr
case class NegExpr(expr: Expr) extends Expr

def stringify(expr: Expr): String = expr match {
  case BinExpr(left, op, right) => s"(${stringify(left)} $op ${stringify(right)})"
  case NegExpr(expr) => s"-${stringify(expr)}"
  case Literal(value) => value.toString
  case Variable(name) => name
}

def simplify(expr: Expr): Expr = {
  val result = expr match {
    case BinExpr(NegExpr(e1), "+", NegExpr(e2)) => NegExpr(simplify(BinExpr(e1, "+", e2)))
    case BinExpr(NegExpr(e1), "-", NegExpr(e2)) => simplify(BinExpr(e2, "-", e1))
    case BinExpr(NegExpr(e1), "*", NegExpr(e2)) => simplify(BinExpr(e1, "*", e2))
    case BinExpr(Literal(v1), "+", Literal(v2)) => Literal(v1 + v2)
    case BinExpr(Literal(v1), "-", Literal(v2)) => Literal(v1 - v2)
    case BinExpr(Literal(v1), "*", Literal(v2)) => Literal(v1 * v2)
    case BinExpr(Literal(0), "+", right) => simplify(right)
    case BinExpr(left, "+", Literal(0)) => simplify(left)
    case BinExpr(Literal(0), "-", right) => NegExpr(simplify(right))
    case BinExpr(left, "-", Literal(0)) => simplify(left)
    case BinExpr(Literal(0), "*", _) | BinExpr(_, "*", Literal(0)) => Literal(0)
    case BinExpr(left, op, right) => BinExpr(simplify(left), op, simplify(right))
    case NegExpr(e) => NegExpr(simplify(e))
    case _ => expr
  }

  if (result == expr) result else simplify(result)
}


// (1 + 1)
val expr1 = BinExpr(Literal(1), "+", Literal(1))
// ((1 + 1) * x)
val expr2 = BinExpr(expr1, "*", Variable("x"))
// ((2 - 1) * x)
val expr3 = BinExpr(BinExpr(Literal(2), "-", Literal(1)), "*", Variable("x"))
// (((1 + 1) * y) + ((1 - 1) * x))
val expr4 = BinExpr(
              BinExpr(
                BinExpr(Literal(1), "+", Literal(1)), "*", Variable("y")),
              "+",
              BinExpr(
                BinExpr(Literal(1), "-", Literal(1)), "*", Variable("x")))

// 0 - x
val expr5 = BinExpr(Literal(0), "-", Variable("x"))
// -x * -y
val expr6 = BinExpr(NegExpr(Variable("x")), "*", NegExpr(Variable("y")))

stringify(expr1)
stringify(expr2)
stringify(expr3)
stringify(expr4)

stringify(simplify(expr1))
stringify(simplify(expr2))
stringify(simplify(expr3))
stringify(simplify(expr4))
stringify(simplify(expr4))
stringify(simplify(expr5))
stringify(simplify(expr6))
