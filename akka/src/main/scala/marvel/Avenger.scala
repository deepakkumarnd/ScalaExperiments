package marvel

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Avenger {

  sealed trait Action

  case object Ready extends Action
  case object Steady extends Action
  case object Go extends Action
  case object Stop extends Action

  def apply(): Behavior[Action] = Behaviors.setup { context =>
    val nickFury = context.spawn(NickFury(), "NickFury")

    Behaviors.receiveMessage { message =>
      message match {
        case Ready =>
          nickFury ! NickFury.Start
          ready()
          Behaviors.same
        case Steady =>
          val hulk = context.spawn(Hulk(), "Hulk")
          nickFury ! NickFury.Report(hulk)
          Behaviors.same
        case Go =>
          go()
          Behaviors.same
        case Stop =>
          nickFury ! NickFury.Stop
          Behaviors.stopped
      }
    }
  }

  private def ready() = println("Ready")
  private def steady() = println("Steady")
  private def go() = println("Go")
}
