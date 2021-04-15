package io.basicobject.kafka

import org.apache.kafka.clients.producer.{
  KafkaProducer,
  ProducerConfig,
  ProducerRecord
}
import org.apache.kafka.common.serialization.StringSerializer

import java.util.Properties
import scala.annotation.tailrec
import scala.concurrent.duration._
import scala.concurrent.{Await, ExecutionContext, Future}
import scala.io.StdIn
import scala.util.Random

object Producer extends App {
  println(
    "Running kafka producer, make sure you have started the kafka server!!"
  )

  println("Type q to quit the application")
  // 1. Create producer properties
  val producerProps = new Properties()
  producerProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092")
  producerProps.put(
    ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    classOf[StringSerializer].getName
  )
  producerProps.put(
    ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    classOf[StringSerializer].getName
  )

  // 2. Create producer
  val producer: KafkaProducer[String, String] =
    new KafkaProducer[String, String](producerProps)

  @tailrec
  def loop(): Unit = {
    // 4. Create producer record
    val message = Random.alphanumeric.take(100).mkString("")
    val record: ProducerRecord[String, String] =
      new ProducerRecord[String, String]("mytopic", message)
    // 5. Send data
    producer.send(record)
    println(
      "Data sent to Kafka, (type q - quit), hit enter to send another message"
    )

    def userInput: Future[String] =
      Future { StdIn.readLine().trim }(ExecutionContext.parasitic)

    val c = Await.result(userInput, 1.second)
    if (c != "q") loop()
  }

  loop()

  producer.flush() // flush data
  producer.close() // flush and close connection
}
