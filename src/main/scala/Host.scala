import control.Gate

case class Host(name: String, queue: Queue) extends Node {

  var clock: Long = 0
  
  var trafficSources: List[TrafficSource] = List.empty
  
  def addTrafficSource(trafficSource: TrafficSource): Unit = {
    this.trafficSources = this.trafficSources :+ trafficSource
  }
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, isAvailable: (lnode: Node, rnode: Node) => Option[Connection]): Unit = {
    
    clock = clock + tick
    
    if (getQueue.isEmpty) {
      queue.notSending()
    }
    else {
      queue.getQueue.foreach(packet => {
        isAvailable(this, packet.nextHop) match {
          case Some(connection): Option[Connection] => {
            queue.removePacket(packet)
            connection.addPackage(packet)
            val timeToSend = packet.size / connection.speed
            val finished = clock + timeToSend
            EventController.addEvent(Event(connection, packet, finished))
            queue.sending(timeToSend)
          }
          case None => queue.blocked(tick)
        }
      })
    }
  }

  def prepareTick(tick: Long): Unit = {
    trafficSources.foreach(_.getAvailableFrame.foreach(p => {
      queue.addPacket(p)
    }))
  }
}