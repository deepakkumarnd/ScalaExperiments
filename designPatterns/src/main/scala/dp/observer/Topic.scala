package dp.observer

class Topic extends Subject {
  private val observers: scala.collection.mutable.Set[Observer] =
    scala.collection.mutable.Set.empty[Observer]

  override def register(observer: Observer): Unit =
    synchronized(observers.add(observer))

  override def unregister(observer: Observer): Unit =
    synchronized(observers.remove(observer))

  override def notifyObservers(): Unit = observers.foreach(_.onUpdate())

  def sendMessage(msg: String): Unit = {
    notifyObservers()
  }

}
