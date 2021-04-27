package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, RunnableGraph, Sink, Source}

import scala.concurrent.Future
import scala.util.{Failure, Success}

object Materialization extends App {

  /**
    * A materializer allocates resources such as memory, file descriptors etc
    * Each component(stage in a graph ie source, flow or sink) can produce a materialized value
    * A materialized value can be anything, such as a an integer, network connection, future etc
    * Different runs creates different materialized values for the same components
    *
    * A single graph produces a single materialized value
    */
  implicit val system = ActorSystem("MaterializingStream")

  // materialized value of simpleGraph is NotUsed
  val simpleGraph: RunnableGraph[NotUsed] =
    Source(1 to 10).to(Sink.foreach(println))

  // Using materialised value of a graph
  val source = Source(1 to 10)
  val sumSink = Sink.reduce[Int] { (a, b) => a + b }

  // here runWith connect a source to sink directly

  //  same as source.to(sumSink).run()
  val sumFuture: Future[Int] = source.runWith(sumSink)

  sumFuture.onComplete {
    case Success(value) =>
      println(s"The sum of elements in the stream is $value")
    case Failure(exception) =>
      println("Failed to compute the sum of all the elements in the stream")
      exception.printStackTrace()
  }(system.dispatcher)

  // choosing materialised value
  // By using via and to the left most materialised value is kept by the graph
  // to have control over which materialized value to be kept you need to use
  // viaMat method

  val simpleSource = Source(1 to 10)
  val simpleFlow = Flow[Int].map(x => x + 1)
  val simpleSink = Sink.foreach[Int](println)

  simpleSource.viaMat(simpleFlow)((sourceMat, flowMat) => flowMat)
  //  or
  val simpleGraph2 =
    simpleSource.viaMat(simpleFlow)(Keep.right).toMat(simpleSink)(Keep.right)

  simpleGraph2
    .run()
    .onComplete {
      case Success(_) => println("Stream processing finished")
      case Failure(e) => println("Stream processing finished with exception")
    }(system.dispatcher)

  // sugars

  Source(1 to 10).runWith(
    Sink.reduce[Int](_ + _)
  ) // same as source.toMat(Sink.reduce)(Keep.right)
  Source(1 to 10).runReduce[Int](_ + _)
  // backwards

  println("Backwards graph")

  val backwardGraph = Sink.foreach[Int](println).runWith(Source.single(42))
  // bothways

  val bothWaysGraph =
    Flow[Int].map(x => x * 2).runWith(simpleSource, simpleSink)

  system.terminate()
}
