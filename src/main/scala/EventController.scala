import scala.::

object EventController {

  private var events: List[Event] = List.empty

  def addEvent(event: Event): Unit = {
    Console.println("received event")
    events = (events :+ event)
  }
  
  def getSortedEvents = events.sortBy(_.endTime)

}
