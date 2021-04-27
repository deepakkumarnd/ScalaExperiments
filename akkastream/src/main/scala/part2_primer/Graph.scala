package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{
  Broadcast,
  Flow,
  GraphDSL,
  RunnableGraph,
  Sink,
  Source,
  Zip
}

import scala.concurrent.ExecutionContext

object Graph extends App {
  implicit val system = ActorSystem("Graphs")
  implicit val ec: ExecutionContext = system.dispatcher

  //  implement y = x^2 + 2x + 1

  val simpleSource = Source(1 to 100)
  val doubleFlow = Flow[Int].map(x => 2 * x)
  val squareFlow = Flow[Int].map(x => x * x)
  val combineFlow = Flow[(Int, Int)].map(x => x._1 + x._2 + 1)
  val simpleSink = Sink.foreach[Int](println)

  val graph = RunnableGraph.fromGraph[NotUsed] {
    // Create a graph from a shape
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] => // mutable builder
      // Provides operators such as ~>
      import GraphDSL.Implicits._

      // Create fan out
      val broadcast = builder.add(Broadcast[Int](2))
      // Create fan in
      val zip = builder.add(Zip[Int, Int])
      // Connect components
      simpleSource ~> broadcast
      broadcast.out(0) ~> doubleFlow ~> zip.in0
      broadcast.out(1) ~> squareFlow ~> zip.in1
      zip.out ~> combineFlow ~> simpleSink
      ClosedShape // freeze builder and create an immutable graph
    } // graph
  } // runnable graph

  graph.run()
}
