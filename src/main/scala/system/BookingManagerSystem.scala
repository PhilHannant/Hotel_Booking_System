package system

import java.util.Date

import scala.collection.concurrent

import bookingManagerExceptions.RoomNotAvailableException

class BookingManagerSystem extends BookingManager  {


  val bookings = new concurrent.TrieMap[String, Booking]
  val rooms: Set[Int] = Set(101, 102, 201, 203)

  /**
    * Return true if there is no booking for the given room on the date,
    * otherwise false
    */
  override def isRoomAvailable(room: Int, date: Date): Boolean = {

    def roomChecker(r: List[Booking], b: Booking): Boolean = r match {
      case x :: xs => if (x == b) {
        false
      } else {
        roomChecker(xs, b)
      }
      case Nil => true
    }

    if (roomIsValid(room)) {
      roomChecker(bookings.values.toList, Booking(room, date))
    } else {
      false
    }
  }

  /**
    * Add a booking for the given guest in the given room on the given
    * date. If the room is not available, throw a suitable Exception.
    */
  override def addBooking(guest: String, room: Int, date: Date): Unit = {
    if (bookings.isEmpty) {
      bookings.put(guest, Booking(room, date))
    } else {
      if (isRoomAvailable(room, date)) {
        bookings.put(guest, Booking(room, date))
      } else {
        throw RoomNotAvailableException(s"Room $room not available")
      }
    }
  }


  def roomIsValid(room: Int): Boolean = {
    rooms.contains(room)
  }

  /**
    * Return a list of all the available room numbers for the given date
    */
  override def getAvailableRooms(date: Date): Seq[Int] = {
    val bookedRooms = bookings.values.filter(b => b.date == date).toSeq
    rooms.filter(r => !bookedRooms.map(_.room).contains(r)).toSeq
  }

}


object BookingManagerSystem {
  def apply(): BookingManagerSystem = new BookingManagerSystem()
}