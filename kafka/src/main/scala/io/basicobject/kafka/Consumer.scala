package io.basicobject.kafka

import org.apache.kafka.clients.consumer.{ConsumerConfig, KafkaConsumer}
import org.apache.kafka.common.serialization.StringDeserializer
import org.slf4j.LoggerFactory

import java.util
import java.util.Properties
import scala.jdk.CollectionConverters._

object Consumer extends App {
  println(
    "Running kafka consumer, please make sure you have kafka running in local"
  )

  val logger = LoggerFactory.getLogger(this.getClass.getSimpleName)

  // Create consumer properties
  val consumerProps = new Properties()

  consumerProps.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  consumerProps.put(
    ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG,
    classOf[StringDeserializer].getName
  )
  consumerProps.put(
    ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG,
    classOf[StringDeserializer].getName
  )

  // name to identify consumer group
  consumerProps.put(ConsumerConfig.GROUP_ID_CONFIG, "my-application1")
  // values earliest, latest, none
  consumerProps.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  // Create a consumer
  val consumer: KafkaConsumer[String, String] =
    new KafkaConsumer[String, String](consumerProps)

  // subscribe to a topic
  // util.Collections.singleton because we have to subscribe to a single topic here
  // Array.toList(topic1, topic2) for multiple topics
  consumer.subscribe(util.Collections.singleton("mytopic"))

  while (true) {
    val consumerRecords = consumer.poll(10).asScala
    consumerRecords.foreach { record =>
      println(s"Key: ${record.key()} Value: ${record.value()}")
    }
  }
}
