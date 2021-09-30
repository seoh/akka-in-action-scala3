package dev.seoh.example.akka

import akka.actor.{Actor, ActorLogging, ActorRef, Props}

object Greeter:
  case class Greeting(message: String)

class Greeter extends Actor with ActorLogging:
  def receive: Receive =
    case Greeter.Greeting(message) =>
      log.info("Hello {}!", message)

object Greeter2:
  def props(ref: ActorRef) = Props(new Greeter2(ref))

class Greeter2(ref: ActorRef) extends Actor with ActorLogging:
  def receive: Receive =
    case Greeter.Greeting(who) =>
      val message = s"Hello $who!"
      log.info(message)
      ref ! message
