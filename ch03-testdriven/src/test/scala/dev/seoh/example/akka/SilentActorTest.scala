package dev.seoh.example.akka

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpecLike

class SilentActorTest
    extends TestKit(ActorSystem("testsystem"))
    with AnyWordSpecLike
    with must.Matchers
    with StopSystemAfterAll:

  "A Silent Actor" must {
    "change state when it receives a message, single threaded" in {
      import SilentActor.*

      val actor = TestActorRef[SilentActor]
      actor ! SilentMessage("whisper")
      actor.underlyingActor.state must (contain("whisper"))
    }

    "change state when it receives a message, multi-threaded" in {
      import SilentActor.*

      val actor = system.actorOf(Props[SilentActor])
      actor ! SilentMessage("whisper1")
      actor ! SilentMessage("whisper2")
      actor ! GetState(testActor)

      expectMsg(Vector("whisper1", "whisper2"))
    }
  }
