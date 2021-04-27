package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.{ClosedShape, FlowShape, SinkShape, SourceShape}
import akka.stream.scaladsl.{
  Balance,
  Broadcast,
  Concat,
  Flow,
  GraphDSL,
  RunnableGraph,
  Sink,
  Source,
  Zip
}

object OpenShape extends App {
  implicit val system = ActorSystem("OpenShape")

  // composite source using graph

  val firstSource = Source(1 to 10)
  val secondSource = Source(20 to 30)

  val sourceGraph = Source.fromGraph {
    GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val concat = builder.add(Concat[Int](2))
      // typing two sources
      firstSource ~> concat
      secondSource ~> concat
      SourceShape(concat.out)
    }
  }

  //  sourceGraph.runWith(Sink.foreach[Int](println))

  /**
    * Composite sink
    */
  val sink1 = Sink.foreach[Int](x => println(s"Printing meaningful1 $x"))
  val sink2 = Sink.foreach[Int](x => println(s"Printing meaningful2 $x"))

  val sinkGraph = Sink.fromGraph {
    GraphDSL.create() { implicit builder =>
      import GraphDSL.Implicits._

      val broadcast = builder.add(Broadcast[Int](2))

      broadcast.out(0) ~> sink1
      broadcast.out(1) ~> sink2

      SinkShape(broadcast.in)
    }
  }

  //  firstSource.to(sinkGraph).run()

  /**
    * Composite flow
    * Write your own flow that's composed of two flows
    *  - one that ads 1 to the number
    *  - one that ads does number * 10
    */
  val flow1 = Flow[Int].map { x => x + 1 }
  val flow2 = Flow[Int].map { x => x * 10 }

  val flowGraph = Flow.fromGraph {
    GraphDSL.create() { implicit builder =>
      // inside this everything operate on shapes not on actual components
      import GraphDSL.Implicits._
      // add shape of a flow
      val shape1 = builder.add(flow1)
      val shape2 = builder.add(flow2)
      // connect shapes
      shape1 ~> shape2
      FlowShape(shape1.in, shape2.out)
    }
  }

  Source(1 to 10).via(flowGraph).to(Sink.foreach[Int](println)).run()

  /**
    * Weired source sink flow
    */
  def flowFromSoruceAndSink[A, B](
      sink: Sink[A, _],
      source: Source[B, _]
  ): Flow[A, B, _] = Flow.fromGraph {
    GraphDSL.create() { implicit builder =>
      val sourceShape = builder.add(source)
      val sinkShape = builder.add(sink)
      FlowShape(sinkShape.in, sourceShape.out)
    }
  }

  // The above weired scenario is available and is technically valid and is
  // available as a standard method in the library
  val f = Flow.fromSinkAndSource(Sink.foreach[String](println), Source(1 to 10))
  // to avoid problems with the above code another method is provided withing the library
  //  Flow.fromSinkAndSourceCoupled()
}
