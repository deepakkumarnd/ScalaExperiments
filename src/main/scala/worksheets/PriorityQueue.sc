import scala.collection.mutable

case class User(hits: Int, username: String)

object User {
  implicit val ordering: Ordering[User] = new Ordering[User] {
    override def compare(x: User, y: User) = x.hits compare y.hits
  }
}

val priorityQueue = mutable.PriorityQueue.empty[User]

priorityQueue.addAll(Seq(
  User(40, "user1"),
  User(20, "user2"),
  User(10, "user3"),
  User(30, "user4"),
  User(310, "user5")
))

// top two hitters
priorityQueue.take(2) //dequeueAll.map[String](_.username).foreach(println)
