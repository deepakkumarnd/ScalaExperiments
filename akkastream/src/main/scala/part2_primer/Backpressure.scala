package part2_primer

import akka.actor.ActorSystem
import akka.stream.OverflowStrategy
import akka.stream.scaladsl.{Flow, Sink, Source}

import scala.concurrent.duration._

object Backpressure extends App {
  implicit val system = ActorSystem("Backpressure")

  // Backpressure is the most important feature of a reactive streams.

  // Elements flow as a response to the demand from the consumers.

  // Fast consumers - all is well
  // Slow consumer - problem
  //  - consumer has to send a signal to the producer to slow down
  //  - As the consumer demand increases producer can produce more
  // This process is called backpressure

  val fastSource = Source(1 to 1000)
  val slowSink = Sink.foreach[Int] { x =>
    Thread.sleep(1000)
    println(s"Sink $x")
  }

//  fastSource
//    .to(slowSink)
//    .run() // works on the same actor so no backpressure is applied

//  fastSource.async
//    .to(slowSink)
//    .run() // backpressure, here the Source slows down

  val simpleFlow = Flow[Int].map { x =>
    println(s"Incoming $x")
    x + 1
  }

  //  fastSource.async.via(simpleFlow).async.to(slowSink).run()

  // using buffering and overflow strategy
  val bufferedFlow = Flow[Int]
    .map { x =>
      println(s"Incoming $x")
      x + 1
    }
    .buffer(10, overflowStrategy = OverflowStrategy.dropHead)

  // here the sink will buffer first 16 elements in the stream by default
  // the bufferedFlow will buffer the last 10 element and rest of them are dropped from the stream
  fastSource.async
    .via(bufferedFlow)
    .async
    .throttle(1, 2 second)
    .to(slowSink)
    .run()

  // manually triggering backpressure using throttling
  //  Source(1 to 100).throttle(2, 2 seconds).to(Sink.foreach(println)).run()

}
