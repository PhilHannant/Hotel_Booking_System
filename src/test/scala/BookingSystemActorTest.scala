import java.text.SimpleDateFormat

import actors.BookingSystemActor
import akka.actor.{ActorRef, ActorSystem, PoisonPill, Props}
import messages._
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
  implicit val timeout = Timeout(5 seconds)

  val bmActorRef: ActorRef = bookingSystemActor

  "isRoomAvailable" should "return a noRoomAvailable" in {
    val isRoomAvailable = bmActorRef ? IsRoomAvailable(101,today)
    val result = Await.result(isRoomAvailable, timeout.duration)
    println(result)
    result should be (NoRoomAvailable())
  }

  "addBookingSuccess" should "return a BookingMade" in {
    val addBookingSuccess = bmActorRef ? AddBooking("Smith",101,today)
    val result = Await.result(addBookingSuccess, timeout.duration)
    println(result)
    result should be (BookingMade())
  }

  "addBookingFailure" should "return an ErrorOccurred" in {
    val addBookingFailure = bmActorRef ? AddBooking("Jones",101,today)
    val result = Await.result(addBookingFailure, timeout.duration)
    println(result)
    result should be (ErrorOccurred())
  }


}

