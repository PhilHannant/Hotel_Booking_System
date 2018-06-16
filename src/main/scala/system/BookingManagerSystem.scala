package system

import java.util.Date

class BookingManagerSystem extends BookingManager  {

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
}
