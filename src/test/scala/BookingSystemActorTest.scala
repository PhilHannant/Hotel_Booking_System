import java.text.SimpleDateFormat

import actors.BookingSystemActor
import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import messages.{AvailableRooms, _}
import akka.util.Timeout
import akka.pattern.ask
import org.scalatest.{FlatSpec, Matchers}

import scala.concurrent.duration._
import scala.language.postfixOps
import scala.concurrent.{Await, ExecutionContext, Future, duration}

class BookingSystemActorTest extends FlatSpec with Matchers {

  val system = ActorSystem("bookingSystem")
  val bookingSystemActor = system.actorOf(Props[BookingSystemActor], "bookingSystemActor")
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")
  val tomorrow  = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-15")
  implicit val timeout = Timeout(5 seconds)

  val bmActorRef: ActorRef = bookingSystemActor

  "IsRoomAvailable" should "return a noRoomAvailable" in {
    val isRoomAvailable = bmActorRef ? IsRoomAvailable(101,today)
    val result = Await.result(isRoomAvailable, timeout.duration)
    println(result)
    result should be (NoRoomAvailable())
  }

  "AddBookingSuccess" should "return a BookingMade" in {
    val addBookingSuccess = bmActorRef ? AddBooking("Smith",101,today)
    val result = Await.result(addBookingSuccess, timeout.duration)
    println(result)
    result should be (BookingMade())
  }

  "AddBookingFailure" should "return an ErrorOccurred" in {
    val addBookingFailure = bmActorRef ? AddBooking("Jones",101,today)
    val result = Await.result(addBookingFailure, timeout.duration)
    println(result)
    result should be (ErrorOccurred())
  }

  "GetAvailableRooms" should "return a AvailableRooms containing a sequence of rooms" in {
    val getAvailableRooms = bmActorRef ? GetAvailableRooms(today)
    val result = Await.result(getAvailableRooms, timeout.duration)
    println(result)
    result should be (AvailableRooms(Vector(102, 201, 203)))
  }

  "Empty GetAvailableRooms" should "return an ErrorOccurred" in {
    bmActorRef ? AddBooking("Jones",101,tomorrow)
    bmActorRef ? AddBooking("Smith",102,tomorrow)
    bmActorRef ? AddBooking("Mottaleb",201,tomorrow)
    bmActorRef ? AddBooking("Davis",203,tomorrow)
    val getAvailableRooms = bmActorRef ? GetAvailableRooms(tomorrow)
    val result = Await.result(getAvailableRooms, timeout.duration)
    println(result)
    result should be (ErrorOccurred())
  }

}

