import control.Gate

case class Host(name: String, queue: Queue) extends Node {

  
  var trafficSources: List[TrafficSource] = List.empty

  var sending = false
  
  def addTrafficSource(trafficSource: TrafficSource): Unit = {
    this.trafficSources = this.trafficSources :+ trafficSource
  }
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, time: Long, isAvailable: ((lnode: Node, rnode: Node) => Option[Connection])): Unit = {
    if (sending) {
      
    }
    else if (getQueue.isEmpty && !sending) {
      queue.notSending()
    }
    else if (!sending && !queue.canSend()) {
      queue.blocked(tick)
    }
    else {
      queue.getQueue.foreach(packet => {
        isAvailable(this, packet.nextHop) match {
          case Some(connection): Option[Connection] => {
            queue.removePacket(packet)
            connection.addPackage(packet)
            val timeToSend = packet.size / connection.speed
            val finished = time + timeToSend
            sending = true
            EventController.addEvent(Event(() => connection.removePackage(packet), finished))
            EventController.addEvent(Event(() => sending = false, finished))
            queue.sending(timeToSend)
          }
          case None => queue.blocked(tick)
        }
      })
    }
  }

  def prepareTick(tick: Long, time: Long): Unit = {
    trafficSources.foreach(_.getAvailableFrame(time, this).foreach(p => {
      queue.addPacket(p)
    }))
  }
}