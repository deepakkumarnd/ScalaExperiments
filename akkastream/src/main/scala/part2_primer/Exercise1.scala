package part2_primer

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

/**
  * Exercise:
  *
  * Create a stream that takes names of persons, then you will
  * take first two names with length > 5 chars.
  */
object Exercise1 extends App {
  implicit val system = ActorSystem("Exercise1")

  val nameSource = Source(
    List(
      "Deepak Kumar",
      "Anil",
      "Raju",
      "Amrutha Deepak",
      "Narayanamoorthy",
      "Sam"
    )
  )

  val filterFlow = Flow[String].filter { name => name.length > 5 }

  val graph = nameSource
    .via(filterFlow)
    .take(2)
    .to(Sink.foreach[String](println))

  graph.run()

  // shorthand

  Thread.sleep(1000)

  nameSource.via(filterFlow).take(2).runForeach(println)

  system.terminate()
}
