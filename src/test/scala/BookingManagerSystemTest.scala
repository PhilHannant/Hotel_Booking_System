import java.text.SimpleDateFormat
import java.util.Date

import org.scalatest._
import system.BookingManagerSystem
import bookingManagerExceptions.{NoRoomsAvailableException, RoomNotAvailableException}

class BookingManagerSystemTest extends FlatSpec with Matchers{

  val bookingSystem = BookingManagerSystem()
  val today: Date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-16")
  val tomorrow: Date = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-17")

  "isRoomValid" should "take an Int and return a Boolean" in {
    bookingSystem.roomIsValid(101) should be (true)
    bookingSystem.roomIsValid(20202) should be (false)
  }

  "isRoomAvailable" should "take an Int and a Date, then return a boolean" in {
    bookingSystem.isRoomAvailable(101, today) should be (true)
  }

  "addBooking1" should "take a string, an Int and a Date, then add them to a TrieMap" in {
    bookingSystem.isRoomAvailable(101, today) should be (true)
    bookingSystem.addBooking("Smith", 101, today)
    bookingSystem.isRoomAvailable(101, today) should be (false)
  }

  "addBooking2" should "throw an exception when a room is not available" in {
    assertThrows[RoomNotAvailableException] {
      bookingSystem.addBooking("Jones", 101, today)
    }
  }

  "addBooking3" should "take a string, an Int and a Date, then add them to a TrieMap" in {
    bookingSystem.addBooking("Bonham", 201, tomorrow) should be (true)
  }

  "getAvailableRooms" should "take a date and return a sequence of Int" in {
    val expected: Seq[Int] = Seq(203)
    bookingSystem.addBooking("Smith", 101, tomorrow)
    bookingSystem.addBooking("Ali", 102, tomorrow)
    bookingSystem.getAvailableRooms(tomorrow) should contain theSameElementsAs expected
  }

  "getAvailableRooms2" should "take a date and return a sequence of Int" in {
    bookingSystem.addBooking("Jackson", 203, tomorrow)
    assertThrows[NoRoomsAvailableException] {
      bookingSystem.getAvailableRooms(tomorrow)
    }
  }


}