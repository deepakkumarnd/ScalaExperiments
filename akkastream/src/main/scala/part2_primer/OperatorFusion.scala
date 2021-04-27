package part2_primer

import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.ExecutionContext

object OperatorFusion extends App {
  implicit val system = ActorSystem("OperatorFusion")

  implicit val ec: ExecutionContext = system.dispatcher

  val simpleSource = Source(1 to 10)
  val simpleFlow1 = Flow[Int].map(_ + 1)
  val simpleFlow2 = Flow[Int].map(_ * 10)
  val simpleSink = Sink.foreach(println)

  // runs on the SAME ACTOR
  // it is just like an actor is running the entire graph
  // this is known as operator fusion, each stage in a graph is known as operator.

  // When individual components are time consuming you could use async boundaries to get more
  // throughput. But it is good to stick with fusion when the operations are not time consuming.

  val simpleGraph =
    simpleSource.via(simpleFlow1).via(simpleFlow2).to(simpleSink)
  simpleGraph.run()

  Thread.sleep(2000)
  // aync boundaries
  // the async operation executes the graph in multiple actors

  // this one guaranty the order of execution of each stages
  println("Synchronous operation")
  Source(1 to 3)
    .map { x => println(s"A $x"); x }
    .map { x => println(s"B $x"); x }
    .map { x => println(s"C $x"); x }
    .run()

  // the async operation makes each of the stages to be executed in different order
  // but the elements in the stream are processed in order for a particular stage.
  Thread.sleep(2000)
  println("Async boundaries")
  Source(1 to 3)
    .map { x => println(s"A $x"); x }
    .async
    .map { x => println(s"B $x"); x }
    .async
    .map { x => println(s"C $x"); x }
    .async
    .run()
  system.terminate()
}
