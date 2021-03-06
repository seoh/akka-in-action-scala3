package dev.seoh.example.akka

import akka.actor.{Actor, ActorRef, Props}
import akka.util.Timeout

import scala.concurrent.Future

object BoxOffice:
  def props(implicit timeout: Timeout): Props = Props(new BoxOffice)
  def name: String = "boxOffice"

  sealed trait EventResponse

  case class CreateEvent(name: String, tickets: Int)

  case class GetEvent(name: String)

  case class GetTickets(event: String, tickets: Int)
  case class CancelEvent(name: String)

  case class Event(name: String, tickets: Int)
  case class Events(events: Vector[Event])

  case class EventCreated(event: Event) extends EventResponse

  case object GetEvents

  case object EventExists extends EventResponse

class BoxOffice(implicit timeout: Timeout) extends Actor:
  import BoxOffice.*
  import context.dispatcher

  def receive: Receive =
    case CreateEvent(name, tickets) =>
      def create() = {
        val eventTickets = createTicketSeller(name)
        val newTickets = (1 to tickets).map { ticketId =>
          TicketSeller.Ticket(ticketId)
        }.toVector
        eventTickets ! TicketSeller.Add(newTickets)
        sender() ! EventCreated(Event(name, tickets))
      }
      context.child(name).fold(create())(_ => sender() ! EventExists)

    case GetTickets(event, tickets) =>
      def notFound() = sender() ! TicketSeller.Tickets(event)
      def buy(child: ActorRef) =
        child.forward(TicketSeller.Buy(tickets))

      context.child(event).fold(notFound())(buy)

    case GetEvent(event) =>
      def notFound() = sender() ! None
      def getEvent(child: ActorRef) = child forward TicketSeller.GetEvent

      context.child(event).fold(notFound())(getEvent)

    case GetEvents =>
      import akka.pattern.{ask, pipe}

      def getEvents = context.children.map { child =>
        self.ask(GetEvent(child.path.name)).mapTo[Option[Event]]
      }
      def convertToEvents(f: Future[Iterable[Option[Event]]]) =
        f.map(_.flatten).map(l => Events(l.toVector))

      pipe(convertToEvents(Future.sequence(getEvents))) to sender()

    case CancelEvent(event) =>
      def notFound() = sender() ! None
      def cancelEvent(child: ActorRef) = child forward TicketSeller.Cancel
      context.child(event).fold(notFound())(cancelEvent)

  def createTicketSeller(name: String): ActorRef =
    context.actorOf(TicketSeller.props(name), name)
