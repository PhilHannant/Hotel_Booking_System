package system

import java.util.Date

import scala.collection.concurrent

class BookingManagerSystem extends BookingManager  {


  var bookings = new concurrent.TrieMap[String, Booking]
  val rooms: Set[Int] = Set(101, 102, 201, 203)

  /**
    * Return true if there is no booking for the given room on the date,
    * otherwise false
    */
  override def isRoomAvailable(room: Int, date: Date): Boolean = ???

  /**
    * Add a booking for the given guest in the given room on the given
    * date. If the room is not available, throw a suitable Exception.
    */
  override def addBooking(guest: String, room: Int, date: Date): Unit = ???


  def roomIsValid(room: Int): Boolean = {
    rooms.contains(room)
  }

}


object BookingManagerSystem {
  def apply(): BookingManagerSystem = new BookingManagerSystem()
}