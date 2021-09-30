package dev.seoh.example.akka

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpecLike
import akka.testkit.CallingThreadDispatcher

import akka.testkit.EventFilter
import com.typesafe.config.ConfigFactory
import akka.actor.UnhandledMessage

class GreeterTest
    extends TestKit(GreeterTest.testSystem)
    with AnyWordSpecLike
    with must.Matchers
    with StopSystemAfterAll:

  "The Greeter" must {
    "say Hello World! when a Greeting(\"World\") is sent to it" in {
      val id = CallingThreadDispatcher.Id
      val props = Props[Greeter].withDispatcher(id)

      val actor = system.actorOf(props)
      EventFilter
        .info(
          message = "Hello World!",
          occurrences = 1
        )
        .intercept {
          actor ! Greeter.Greeting("World")
        }
    }

    "say something else and see what happens" in {
      val props = Greeter2.props(testActor)
      val actor = system.actorOf(props, "greeter2-1")

      system.eventStream.subscribe(testActor, classOf[UnhandledMessage])
      actor ! "World"

      expectMsg(UnhandledMessage("World", system.deadLetters, actor))
    }
  }
end GreeterTest

object GreeterTest:
  val testSystem = {
    val config = ConfigFactory.parseString("""
      akka.loggers = [akka.testkit.TestEventListener]
    """)

    ActorSystem("testsystem", config)
  }
