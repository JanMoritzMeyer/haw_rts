import control.OpenGate

case class Switch(name: String) extends Node {

  val queue = new Queue(OpenGate())

  var sending = false
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, time: Long, availabilites: (Node, Node) => Option[Connection]): Unit = {
    queue.getQueue.foreach(p => {
      availabilites(this, p.nextHop) match
        case Some(connection) => {
          queue.removePacket(p)
          connection.addPackage(p)
          val timeToSend = p.size / connection.speed
          val finished = time + timeToSend
          sending = true
          EventController.addEvent(Event(() => connection.removePackage(p), finished))
          EventController.addEvent(Event(() => sending = false, finished))
        }
        case None => {
          
        }
    })
  }

  override def prepareTick(tick: Long, time: Long): Unit = {
    // nothing to do
  }
}