package part2_primer

import akka.actor.ActorSystem
import akka.stream.{ClosedShape, UniformFanInShape}
import akka.stream.scaladsl.{GraphDSL, RunnableGraph, Sink, Source, ZipWith}

import scala.concurrent.ExecutionContext

object MoreOpenGraphs extends App {
  implicit val system = ActorSystem("MoreOpenGraphs")
  implicit val ec: ExecutionContext = system.dispatcher

  // Max3 operator
  // - 3 input of type int
  // - maximum of 3

  val max3Graph = GraphDSL.create() { implicit builder =>
    import GraphDSL.Implicits._
    val max1 = builder.add(ZipWith[Int, Int, Int]((a, b) => Math.max(a, b)))
    val max2 = builder.add(ZipWith[Int, Int, Int]((a, b) => Math.max(a, b)))

    max1.out ~> max2.in0

    UniformFanInShape(max2.out, max1.in0, max1.in1, max2.in1)
  }

  val source1 = Source(1 to 10)
  val source2 = Source(10 to 1 by -1)
  val source3 = Source(List.fill[Int](10)(5))
  val maxSink = Sink.foreach[Int](println)

  val max3GraphRunnable = RunnableGraph.fromGraph {
    GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val max = builder.add(max3Graph)
      source1 ~> max.in(0)
      source2 ~> max.in(1)
      source3 ~> max.in(2)
      max.out ~> maxSink
      ClosedShape
    }
  }

  max3GraphRunnable.run()
}
