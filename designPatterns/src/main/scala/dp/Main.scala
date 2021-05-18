package dp

import observer._

object Main extends App {
  val topic = new Topic()
  val observer1 = new TopicObserver(topic)
  val observer2 = new TopicObserver(topic)
  val observer3 = new TopicObserver(topic)
  topic.register(observer1)
  topic.register(observer2)
  topic.register(observer3)
  topic.sendMessage("Hello")
}
