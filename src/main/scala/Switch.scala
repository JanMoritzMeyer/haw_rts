import control.Gate

case class Switch(name: String, gates: Map[Int, Gate] = Map.empty, frer: Boolean = false) extends Node {

  val queue = new ClassicQueue(gates)

  var sending = false

  var frer_list: Seq[Int] = Seq.empty
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, time: Long, availabilities: (Node, Node) => Option[Connection]): Unit = {
    queue.getQueue(time).foreach(p => {
      availabilities(this, p.nextHop) match
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

  override def addToQueue(packet: Packet): Unit = {
    if (frer) {
      if (frer_list.contains(packet.uid)) {
        Console.println("threw away frer")
      } else {
        frer_list = frer_list :+ packet.uid
        queue.addPacket(packet)
      }
    }
    else {
      queue.addPacket(packet)
    }
  }

  override def prepareTick(tick: Long, time: Long): Unit = {
    // nothing to do
  }
}