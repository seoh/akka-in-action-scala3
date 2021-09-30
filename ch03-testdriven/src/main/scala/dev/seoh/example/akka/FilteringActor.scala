package dev.seoh.example.akka

import akka.actor.{Actor, ActorRef, Props}

object FilteringActor:
  def props(ref: ActorRef, size: Int) = Props(new FilteringActor(ref, size))

  case class Event(id: Long)

class FilteringActor(nextActor: ActorRef, bufferSize: Int) extends Actor:
  import FilteringActor.*

  var lastMessages = Vector.empty[Event]
  def receive: Receive =
    case msg: Event =>
      if !lastMessages.contains(msg) then
        lastMessages = lastMessages :+ msg
        nextActor ! msg

        if lastMessages.size > bufferSize then lastMessages = lastMessages.tail
end FilteringActor
