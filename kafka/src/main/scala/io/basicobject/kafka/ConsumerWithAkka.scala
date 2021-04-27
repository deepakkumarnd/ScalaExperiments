package io.basicobject.kafka

import akka.actor.ActorSystem
import akka.kafka.scaladsl.Committer
import akka.kafka.scaladsl.Consumer.DrainingControl
import akka.kafka.{CommitterSettings, ConsumerSettings, Subscriptions}
import akka.stream.ActorMaterializer
import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer

import scala.concurrent.Future

object ConsumerWithAkka extends App {
  val system: ActorSystem = ActorSystem()
  val mat: ActorMaterializer = ActorMaterializer()(system)

  val consumerSettings = ConsumerSettings(
    system,
    new StringDeserializer(),
    new StringDeserializer()
  ).withBootstrapServers("localhost:9092")
    .withGroupId("my-application-1")
    .withProperty(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true")
    .withProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest")

  val committerSettings = CommitterSettings(system)

  def processMessage(key: String, value: String): Future[Unit] =
    Future {
      println(s"Key: ${key} Value : ${value}")
    }(system.dispatcher)

  val consumer = akka.kafka.scaladsl.Consumer
    .committableSource(
      consumerSettings,
      Subscriptions.topics("mytopic")
    )
    .mapAsync(10) { msg =>
      processMessage(msg.record.key(), msg.record.value()).map(_ =>
        msg.committableOffset
      )(system.dispatcher)
    }
    .toMat(Committer.sink(committerSettings))(DrainingControl.apply)
    .run()(mat)
}
