package dev.seoh.example.akka

import spray.json.*

case class EventDescription(tickets: Int) {
  require(tickets > 0)
}

case class TicketRequest(tickets: Int) {
  require(tickets > 0)
}

case class Error(message: String)

trait EventMarshalling extends DefaultJsonProtocol:
  import BoxOffice.*

  implicit val eventDescriptionFormat: RootJsonFormat[EventDescription] = jsonFormat1(EventDescription.apply)
  implicit val eventFormat: RootJsonFormat[Event] = jsonFormat2(Event.apply)
  implicit val eventsFormat: RootJsonFormat[Events] = jsonFormat1(Events.apply)
  implicit val ticketRequestFormat: RootJsonFormat[TicketRequest] = jsonFormat1(TicketRequest.apply)
  implicit val ticketFormat: RootJsonFormat[TicketSeller.Ticket] = jsonFormat1(TicketSeller.Ticket.apply)
  implicit val ticketsFormat: RootJsonFormat[TicketSeller.Tickets] = jsonFormat2(TicketSeller.Tickets.apply)
  implicit val errorFormat: RootJsonFormat[Error] = jsonFormat1(Error.apply)
