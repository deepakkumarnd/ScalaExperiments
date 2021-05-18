package dp.observer

trait Subject {
  def register(observer: Observer)
  def unregister(observer: Observer)
  def notifyObservers(): Unit
}
