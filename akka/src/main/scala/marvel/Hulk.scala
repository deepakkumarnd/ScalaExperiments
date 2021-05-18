package marvel

import akka.actor.typed.Behavior
import akka.actor.typed.scaladsl.Behaviors

object Hulk {
  sealed trait Action
  case class Smash(thing: String) extends Action
  case object Stop extends Action

  def apply(): Behavior[Action] = Behaviors.receiveMessage { message =>
    message match {
      case Smash(thing) => println("Smashing " + thing)
      case Stop         => Behaviors.stopped
    }
    Behaviors.same
  }
}
