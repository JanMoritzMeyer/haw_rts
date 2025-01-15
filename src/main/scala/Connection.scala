import control.{Gate, OpenGate}

case class Connection(lnode: Node, lport: Int, rnode: Node, rport: Int, speed: Double){

  var queue: Queue = Queue(OpenGate())

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
    val nextHops = packet.target.filter(packet.nextHop != _).map(t => Network.routingTable(packet.nextHop)(t))
    val sameNextHop = nextHops.forall(_ == nextHops.head)
    if (sameNextHop) {
      if (packet.nextHop != packet.target.head) {
        val nextHop = Network.routingTable(packet.nextHop)(packet.target.head)
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, packet.target, packet.pcp, packet.stream))
      }
      else {
      }
      queue.removePacket(packet)
    }
    else {
      packet.target.foreach(target => {
        val nextHop = Network.routingTable(packet.nextHop)(target)
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, List(target), packet.pcp, packet.stream))
        queue.removePacket(packet)
      })
    }
  }

}
