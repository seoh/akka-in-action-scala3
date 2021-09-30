package dev.seoh.example.akka

import akka.actor.ActorSystem
import akka.testkit.TestKit
import org.scalatest.matchers.*
import org.scalatest.wordspec.AnyWordSpecLike

import scala.util.Random

class SendingActorTest
    extends TestKit(ActorSystem("testsystem"))
    with AnyWordSpecLike
    with must.Matchers
    with StopSystemAfterAll:

  "A Sending Actor" must {
    "send a message to another actor when it has finished processing" in {
      import SendingActor.*

      val props = SendingActor.props(testActor)
      val actor = system.actorOf(props, "sendingActor")

      val size = 1000
      val maxInclusive = 100_000

      def randomEvents() =
        (0 until size)
          .map(_ => Event(Random.nextInt(maxInclusive)))
          .toVector

      val unsorted = randomEvents()
      val sortEvents = SortEvents(unsorted)
      actor ! sortEvents
      expectMsgPF() { case SortedEvent(events) =>
        events.size must be(size)
        unsorted.sortBy(_.id) must be(events)
      }
    }
  }
