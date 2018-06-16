import java.text.SimpleDateFormat


import org.scalatest._
import system.{Booking, BookingManagerSystem}
import bookingManagerExceptions.RoomNotAvailableException

class BookingManagerSystemTest extends FlatSpec with Matchers{

  val bookingSystem = BookingManagerSystem()
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")

  "a isRoomValid" should "take an Int and return a Boolean" in {
    bookingSystem.roomIsValid(101) should be (true)
    bookingSystem.roomIsValid(20202) should be (false)
  }

  "a isRoomAvailable" should "take an Int and a Date, then return a boolean" in {
    bookingSystem.isRoomAvailable(101, today) should be (true)
  }

  "a addBooking" should "take a string, an Int and a Date, then add them to a TrieMap" in {
    bookingSystem.isRoomAvailable(101, today) should be (true)
    bookingSystem.addBooking("Smith", 101, today)
    bookingSystem.isRoomAvailable(101, today) should be (false)
  }

  "a addBooking" should "throw an exception when a room is not available" in {
    assertThrows[RoomNotAvailableException] {
      bookingSystem.addBooking("Jones", 101, today)
    }
  }



}