import java.text.SimpleDateFormat

import org.scalatest._
import system.BookingManagerSystem

class BookingManagerSystemTest extends FlatSpec with Matchers{

  val bookingSystem = BookingManagerSystem()
  val today = new SimpleDateFormat("yyyy-MM-dd").parse("2018-06-14")

  "a isRoomValid" should "take an Int and return a Boolean" in {
    bookingSystem.roomIsValid(101) should be (true)
    bookingSystem.roomIsValid(20202) should be (false)
  }

  "a BookingManagerSystem" should "take an Int and a Date, then return a boolean" in {
    bookingSystem.isRoomAvailable(101, today) should be (true)
  }


}