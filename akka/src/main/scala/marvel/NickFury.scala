package marvel

import akka.actor.typed.{ActorRef, Behavior}
import akka.actor.typed.scaladsl.Behaviors

object NickFury {
  sealed trait Action
  case class Report(avanger: ActorRef[Hulk.Action]) extends Action
  case object Start extends Action
  case object Stop extends Action

  def apply(): Behavior[Action] = Behaviors.receiveMessage { message =>
    message match {
      case Start =>
        println("Nick Fury is in command")
        Behaviors.same
      case Report(avenger) =>
        println("Nick Fury: Giving order")
        avenger ! Hulk.Smash("Building")
        Behaviors.same
      case Stop =>
        println("Nick Fury: Resigning")
        Behaviors.stopped
    }
  }
}
