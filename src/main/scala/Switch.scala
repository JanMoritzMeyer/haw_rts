import control.Gate

case class Switch(name: String, gates: Map[Int, Gate] = Map.empty) extends Node {

  val queue = new Queue(gates)

  var sending = false
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, time: Long, availabilites: (Node, Node) => Option[Connection]): Unit = {
    queue.getQueue(time).foreach(p => {
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