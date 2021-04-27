package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{Broadcast, GraphDSL, RunnableGraph, Sink, Source}

object Exercise3 extends App {
  implicit val system = ActorSystem("Example3")

  /**
    * Feed a source to 2 sinks at the same time
    */
  val source = Source(1 to 100)

  val sink1 = Sink.foreach[Int](x => println(s"First sink $x"))
  val sink2 = Sink.foreach[Int](x => println(s"Second sink $x"))
//  val sink2 = Sink.fold[Int, Int](0) { (a, b) => a + b }

  val graph = RunnableGraph.fromGraph[NotUsed] {
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val broadcast = builder.add(Broadcast[Int](2))
      source ~> broadcast
      broadcast.out(0) ~> sink1
      broadcast.out(1) ~> sink2
      ClosedShape
    }
  }

  graph.run()

}
