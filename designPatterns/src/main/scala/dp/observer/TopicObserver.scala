package dp.observer

class TopicObserver(subject: Subject) extends Observer {
  override def onUpdate(): Unit = {
    println(
      this.getClass.getSimpleName + ": received update on subject -> " + subject
    )
  }
}
