package dev.seoh.example.akka

import akka.actor.{Actor, PoisonPill, Props}

object TicketSeller:
  def props(event: String) = Props(new TicketSeller(event))

  case class Add(tickets: Vector[Ticket])
  case class Buy(tickets: Int)
  case class Ticket(id: Int)
  case class Tickets(event: String, entries: Vector[Ticket] = Vector.empty[Ticket])
  case object GetEvent
  case object Cancel

class TicketSeller(event: String) extends Actor:
  import TicketSeller.*

  var tickets = Vector.empty[Ticket]

  def receive: Receive =
    case Add(newTickets) => tickets = tickets ++ newTickets
    case Buy(nOfTickets) =>
      val entries = tickets.take(nOfTickets).toVector
      if entries.size >= nOfTickets then
        sender ! Tickets(event, entries)
        tickets = tickets.drop(nOfTickets)
      else sender ! Tickets(event)

    case GetEvent => sender ! Some(BoxOffice.Event(event, tickets.size))
    case Cancel =>
      sender ! Some(BoxOffice.Event(event, tickets.size))
      self ! PoisonPill
