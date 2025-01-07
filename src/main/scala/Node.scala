trait Node {

  def getQueue: Queue
  def name: String
  def tick(tick: Long, time: Long, availabilites: (Node, Node) => Option[Connection]): Unit
  def prepareTick(tick: Long, time: Long): Unit
  def addToQueue(packet: Packet): Unit = {
    getQueue.addPacket(packet)
  }

}
