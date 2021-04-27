package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.ClosedShape
import akka.stream.scaladsl.{
  Balance,
  GraphDSL,
  Merge,
  RunnableGraph,
  Sink,
  Source
}

import scala.concurrent.duration.DurationInt

object Exercise4 extends App {
  implicit val system = ActorSystem("Exercise4")

  /**
    * Balance graph
    */
  val fastSource = Source(1 to 10).throttle(5, 1 second)
  val slowSource = Source(20 to 29).throttle(2, 1 second)

  val sink1 = Sink.foreach[Int](x => println(s"Sink1 printing $x"))
  val sink2 = Sink.foreach[Int](x => println(s"Sink2 printing $x"))

  val graph = RunnableGraph.fromGraph {
    GraphDSL.create() { implicit builder: GraphDSL.Builder[NotUsed] =>
      import GraphDSL.Implicits._
      val balancer = builder.add(Balance[Int](1))

      val merge = builder.add(Merge[Int](2))

      fastSource ~> merge.in(0)
      slowSource ~> merge.in(1)

      merge ~> balancer

      balancer.out(0) ~> sink1
//      balancer.out(1) ~> sink2

      ClosedShape
    }
  }

  graph.run()
}
