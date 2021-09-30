package dev.seoh.example.akka

import akka.actor.{Actor, ActorRef}

object SilentActor:
  case class SilentMessage(data: String)
  case class GetState(receiver: ActorRef)
end SilentActor

class SilentActor extends Actor:
  import SilentActor.*
  var internalState = Vector.empty[String]

  def receive: Receive =
    case SilentMessage(data) =>
      internalState = internalState :+ data
    case GetState(receiver) =>
      receiver ! internalState

  def state = internalState
end SilentActor
