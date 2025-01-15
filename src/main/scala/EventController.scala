import scala.::

object EventController {

  private var events: List[Event] = List.empty

  def addEvent(event: Event): Unit = {
    events = (events :+ event)
  }
  
  def getSortedEvents: List[Event] = events.sortBy(_.endTime)

}
