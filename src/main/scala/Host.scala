case class Host(name: String, queue: Queue) extends Node {

  
  var trafficSources: List[Source] = List.empty

  var sending = false
  
  def addTrafficSource(trafficSource: Source): Unit = {
    this.trafficSources = this.trafficSources :+ trafficSource
  }
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, time: Long, isAvailable: (lnode: Node, rnode: Node) => Option[Connection]): Unit = {
    val packets = queue.getQueue(time)
    if (queue.isEmpty && !sending) {
      queue.notSending(tick)
    }
    else {
      packets.foreach(packet => {
        isAvailable(this, packet.nextHop) match {
          case Some(connection): Option[Connection] => {
            queue.removePacket(packet)
            connection.addPackage(packet)
            val timeToSend = packet.size / connection.speed
            val finished = time + timeToSend
            sending = true
            EventController.addEvent(Event(() => connection.removePackage(packet), finished))
            EventController.addEvent(Event(() => sending = false, finished))
            queue.sending(timeToSend, packet.pcp)
          }
          case None => queue.notSending(tick)
        }
      })
    }
  }

  def prepareTick(tick: Long, time: Long): Unit = {
    trafficSources.foreach(_.getAvailableFrame(time, tick, this).foreach(p => {
      queue.addPacket(p)
    }))
  }
}