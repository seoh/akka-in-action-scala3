package dev.seoh.example.akka

import akka.testkit.TestKit
import akka.actor.ActorSystem
import org.scalatest.wordspec
import akka.testkit.ImplicitSender
import org.scalatest.matchers.must
import akka.actor.Props

class EchoActorTest
    extends TestKit(ActorSystem("testsystem"))
    with wordspec.AnyWordSpecLike
    with must.Matchers
    with ImplicitSender
    with StopSystemAfterAll:

  "An EchoActor" must {
    "reply with the same message it receives without ask" in {
      val actor = system.actorOf(Props[EchoActor], "echo2")
      val msg = "some message"
      actor ! msg
      expectMsg(msg)
    }
  }
