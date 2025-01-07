import control.{Gate, OpenGate}

case class Connection(lnode: Node, lport: Int, rnode: Node, rport: Int, speed: Int){

  var queue: Queue = Queue(OpenGate())

  def isAvailable: Boolean = queue.isEmpty

  def addPackage(packet: Packet): Unit = {
    if (isAvailable) {
      Console.println(s"successfully queued package with target ${packet.target.name} to ${packet.nextHop.name} with pcp ${packet.pcp}")
      queue.addPacket(packet)
    } else {
      throw new IllegalAccessException("queue was not empty")
    }
  }
  
  def removePackage(packet: Packet): Any = {
    if (packet.nextHop != packet.target) {
      val nextHop = Network.routingTable(packet.nextHop)(packet.target)
      packet.nextHop.addToQueue(Packet(packet.size, nextHop, packet.target, packet.pcp))
    }
    Console.println(s"successfully sent package with target ${packet.target.name} to ${packet.nextHop.name} with pcp ${packet.pcp}")
    queue.removePacket(packet)
  }

}
