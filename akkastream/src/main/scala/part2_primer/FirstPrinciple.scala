package part2_primer

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.Future

object FirstPrinciple extends App {
  implicit val system = ActorSystem("FirstPrinciple")

  val source = Source(1 to 10)
  val sink = Sink.foreach[Int](println)

  val graph = source.to(sink)

  graph.run()

  // flows transform the stream

  Thread.sleep(1000)

  val flow = Flow[Int].map(x => x * x)

  val graph2 = source.via(flow).to(sink)

  graph2.run()

  // sources can emit any kind of messages as long as they are immutable and
  // serializable, but nulls are not allowed

  // Throws an exception
  // val illegalSource = Source.single(null)
  // illegalSource.to(Sink.foreach(println)).run()

  val fintiteSource = Source.single(1)
  val anotherFiniteSource = Source(List(1, 2, 3, 4))
  val emptySoruce = Source.empty[Int]
  val infiniteSoruce = Source(Stream.from(1))
  val soruceFromFuture = Source.fromFuture(Future(42)(system.dispatcher))

  // sinks

  val boringSink = Sink.ignore
  val foreachSink = Sink.foreach[Int](println)
  val headSink = Sink.head[Int] // retrieves the head and closes the stream
  val foldSink = Sink.fold[Int, Int](0) { (a, b) =>
    a + b
  } // sum of all the elements in the steam

  val mapFlow = Flow[Int].map(x => 2 * x)
  val takeFlow = Flow[Int].take(5)
  //  drop, filter
  // Don't have flatMap

  val doubleFlowGraph = source.via(mapFlow).via(takeFlow).to(sink)
  doubleFlowGraph.run()

  // syntactic sugars

  val mapSource = Source(1 to 10).map(x => 2 * x) // is equal to Source(1 to 10).via(mapFlow)
  mapSource.runForeach(println) // is equal to mapSource.to(sink).run()

  system.terminate()
}
