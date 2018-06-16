import java.text.SimpleDateFormat

import actors.BookingSystemActor
import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import messages.{AddBooking, IsRoomAvailable}

object BookingSystemActorTest extends App{

  val system = ActorSystem("bookingSystem")
  val bookingSystemActor = system.actorOf(Props[BookingSystemActor], "bookingSystemActor")
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")

  val bmActorRef: ActorRef = bookingSystemActor
  bmActorRef ! IsRoomAvailable(101,today) // outputs RoomAvaiable()
  bmActorRef ! AddBooking("Smith",101,today) // outputs BookingMade()
  bmActorRef ! AddBooking("Jones",101,today) // outputs ErrorOccurred()

  bmActorRef ! PoisonPill
  system.terminate()

}

