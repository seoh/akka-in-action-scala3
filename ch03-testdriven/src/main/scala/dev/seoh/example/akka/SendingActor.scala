package dev.seoh.example.akka

import akka.actor.{Actor, ActorRef, Props}

object SendingActor:
  def props(ref: ActorRef) = Props(new SendingActor(ref))

  case class Event(id: Long)
  case class SortEvents(unsorted: Vector[Event])
  case class SortedEvent(sorted: Vector[Event])
end SendingActor

class SendingActor(receiver: ActorRef) extends Actor:
  import SendingActor.*

  def receive: Receive =
    case SortEvents(unsorted) =>
      receiver ! SortedEvent(unsorted.sortBy(_.id))
