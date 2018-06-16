package actors

import akka.actor.{Actor, ActorLogging}
import system.BookingManagerSystem

class BookingSystemActor extends Actor with ActorLogging {

  val bookingSystem = BookingManagerSystem ()

  override def receive: Receive = ???
}
