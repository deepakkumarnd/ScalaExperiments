package marvel

import akka.actor.typed.ActorSystem
import marvel.Avenger.{Go, Ready, Steady, Stop}

object Marvel extends App {

  val system = ActorSystem(Avenger(), "marvel-universe")

  system ! Ready
  system ! Steady
  system ! Stop

}
