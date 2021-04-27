package playground

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Sink, Source}

object Playground extends App {
  implicit val actorSystem = ActorSystem("playground")

  Source.single("Hello akka stream").to(Sink.foreach(println)).run()

  actorSystem.terminate()
}
