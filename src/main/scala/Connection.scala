import control.{Gate, OpenGate}

case class Connection(lnode: Node, lport: Int, rnode: Node, rport: Int, speed: Double){

  var queue: Queue = ClassicQueue(Map.empty)

  def isAvailable: Boolean = queue.isEmpty

  def addPackage(packet: Packet): Unit = {
    if (isAvailable) {
      queue.addPacket(packet)
    } else {
      throw new IllegalAccessException("queue was not empty")
    }
  }
  
  def removePackage(packet: Packet): Any = {
    Logging.writePackageLog(packet.nextHop, packet)
    val nextHops = packet.target.filter(packet.nextHop != _).flatMap(t => Network.routingTable(packet.nextHop)(t))
    val sameNextHop = nextHops.forall(_ == nextHops.head)
    if (sameNextHop) {
      if (packet.nextHop != packet.target.head) {
        val nextHop = Network.routingTable(packet.nextHop)(packet.target.head).head
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, packet.target, packet.pcp, packet.stream, packet.uid))
      }
      else {
      }
      queue.removePacket(packet)
    }
    else {
      nextHops.foreach(nextHop => {
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, packet.target, packet.pcp, packet.stream, packet.uid))
        queue.removePacket(packet)
      })
    }
  }

}
