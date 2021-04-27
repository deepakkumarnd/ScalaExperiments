package part2_primer

import akka.NotUsed
import akka.actor.ActorSystem
import akka.stream.scaladsl.{Flow, Keep, Sink, Source}

object Exercise2 extends App {

  /**
    * - Return the last element out of a source
    * - Total word count out of a stream of sentences
    */
  implicit val system = ActorSystem("Exercise2")

  // last element
  val source = Source(1 to 100)

  // method1
  val lastElem = source.runWith(Sink.last)
  lastElem.map(println)(system.dispatcher)
  // method2
  val lastElem2 = source.toMat(Sink.last)(Keep.right).run()
  lastElem2.map(println)(system.dispatcher)

  // Word count

  val sentenceSource = Source(
    Seq("I am cool", "well done", "scala is cool", "barking dogs seldom bites")
  )

  val sentenceCounter1 =
    sentenceSource.map(sentence => sentence.split(" ").length).runReduce {
      (a, b) => a + b
    }

  sentenceCounter1.map(println)(system.dispatcher)

  val sentenceCounter2 =
    sentenceSource
      .map(sentence => sentence.split(" ").length)
      .runWith(Sink.reduce[Int](_ + _))

  sentenceCounter2.map(println)(system.dispatcher)

  val countWords = Flow[String].map(x => x.split(" ").length)
  val wordCountFlow = Flow[String].fold[Int](0) { (count, sentence) =>
    count + sentence.split(" ").length
  }

  val sentenceCounter3 =
    sentenceSource
      .via(countWords)
      .runFold(0) { (a, b) => a + b }

  sentenceCounter3.map(println)(system.dispatcher)

  val sentenceCounter4 = sentenceSource
    .viaMat(countWords)(Keep.right)
    .toMat(Sink.fold(0) { (a, b) => a + b })(Keep.right)
    .run()

  sentenceCounter4.map(println)(system.dispatcher)

  val sentenceCounter5 = sentenceSource.runWith(Sink.fold[Int, String](0) {
    (currentCount, sentence) => currentCount + sentence.split(" ").length
  })

  sentenceCounter5.map(println)(system.dispatcher)

  val sentenceCounter6 = wordCountFlow.runWith(sentenceSource, Sink.head)
  sentenceCounter6._2.map(println)(system.dispatcher)

  system.terminate()
}
