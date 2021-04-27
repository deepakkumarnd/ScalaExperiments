package playground

import akka.actor.SupervisorStrategy.{Restart, Stop}
import akka.actor.{
  Actor,
  ActorLogging,
  ActorSystem,
  OneForOneStrategy,
  PoisonPill,
  Props,
  Stash,
  SupervisorStrategy
}
import akka.util.Timeout

import scala.concurrent.ExecutionContext

object AkkaRecap extends App {
  class SimpleActor extends Actor with Stash with ActorLogging {
    override def receive: Receive = {
      case "change"    => context.become(anotherHandler)
      case "exception" => throw new RuntimeException("Runtime exception")
      case "question"  => sender() ! "answer"
      case "answer"    => println("Got the answer from actor")
      case "stash"     => stash()
      case "unstash" =>
        unstashAll()
        context.become(anotherHandler)
      case message => println(s"Received message ${message}")
    }

    def anotherHandler: Receive = {
      case "change" => context.become(receive)
      case message  => println(s"In another receive handler ${message}")
    }

    override def postStop(): Unit = {
      log.info("Killing with poison")
      println("Got poison pill")
    }

    override def supervisorStrategy: SupervisorStrategy = OneForOneStrategy() {
      case _: RuntimeException =>
        println("Got run time exception, restarting actor")
        Restart
      case _ => Stop
    }
  }

  val system = ActorSystem("AkkaRecap")

  // creating an actor
  val actor = system.actorOf(Props[SimpleActor])
  // Sending message to actor
  // messages are asynchronously

  actor ! "Hello"
  actor ! "change"
  actor ! "Hello"
  actor ! "change"
  actor ! "Hello"
//  actor ! "stash"   //  I don't know what it does
  actor ! "hello"
//  actor ! "unstash" //  I don't know what it does
  actor ! "hello"
  // scheduling
  import scala.concurrent.duration._
  system.scheduler.scheduleOnce(2 seconds) {
    actor ! "Delayed message"
  }(system.dispatcher)

  import akka.pattern.ask
  import akka.pattern.pipe

  val anotherActor = system.actorOf(Props[SimpleActor], "anotherActor")
  implicit val ec: ExecutionContext = system.dispatcher
  implicit val timeout = Timeout(1 seconds)
  val future = actor ? "question"

  future.mapTo[String].pipeTo(anotherActor)

  // Kill actor

  Thread.sleep(3000)
  actor ! "exception"
  actor ! PoisonPill
  anotherActor ! PoisonPill

  system.terminate()
}
