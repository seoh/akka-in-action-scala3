package dev.seoh.example.akka

import akka.actor.{ActorSystem, Props}
import akka.testkit.{TestActorRef, TestKit}
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpecLike

class FilteringActorTest
    extends TestKit(ActorSystem("testsystem"))
    with AnyWordSpecLike
    with must.Matchers
    with StopSystemAfterAll:

  "A Filtering Acotr" must {
    "filter out particular messages" in {
      import FilteringActor.*

      val props = FilteringActor.props(testActor, 5)
      val actor = system.actorOf(props, "filter-1")
      actor ! Event(1)
      actor ! Event(2)
      actor ! Event(1)
      actor ! Event(3)
      actor ! Event(1)
      actor ! Event(4)
      actor ! Event(5)
      actor ! Event(5)
      actor ! Event(6)

      val eventIds = receiveWhile() {
        case Event(id) if id <= 5 => id
      }

      eventIds must be(List(1, 2, 3, 4, 5))
      expectMsg(Event(6))
    }

    "filter out particular messages using expectNoMsg" in {
      import FilteringActor.*

      val props = FilteringActor.props(testActor, 5)
      val actor = system.actorOf(props, "filter-2")

      actor ! Event(1)
      actor ! Event(2)
      expectMsg(Event(1))
      expectMsg(Event(2))

      actor ! Event(1)
      expectNoMessage

      actor ! Event(3)
      expectMsg(Event(3))

      actor ! Event(1)
      expectNoMessage

      actor ! Event(4)
      actor ! Event(5)
      actor ! Event(5)
      expectMsg(Event(4))
      expectMsg(Event(5))
      expectNoMessage
    }
  }

end FilteringActorTest
