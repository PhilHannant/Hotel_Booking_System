import java.text.SimpleDateFormat

import actors.BookingSystemActor
import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import messages.{AddBooking, IsRoomAvailable}
import akka.util.Timeout
import akka.pattern.ask

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, ExecutionContext, Future, duration}

object BookingSystemActorTest extends App{

  val system = ActorSystem("bookingSystem")
  val bookingSystemActor = system.actorOf(Props[BookingSystemActor], "bookingSystemActor")
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")
  implicit val timeout = Timeout(5 seconds)

  val bmActorRef: ActorRef = bookingSystemActor

  val isRoomAvailable = bmActorRef ? IsRoomAvailable(101,today)
  println(Await.result(isRoomAvailable, timeout.duration))

  val addBookingSuccess = bmActorRef ? AddBooking("Smith",101,today)
  println(Await.result(addBookingSuccess, timeout.duration))

  val addBookingFailure = bmActorRef ? AddBooking("Jones",101,today)
  println(Await.result(addBookingFailure, timeout.duration))

  bmActorRef ! PoisonPill
  system.terminate()

}

