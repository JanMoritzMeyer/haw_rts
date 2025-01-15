import control.{Gate, OpenGate}

case class Connection(lnode: Node, lport: Int, rnode: Node, rport: Int, speed: Double){

  var queue: Queue = Queue(OpenGate())

  def isAvailable: Boolean = queue.isEmpty

  def addPackage(packet: Packet): Unit = {
    if (isAvailable) {
      Console.println(s"successfully queued package with target ${packet.target.map(_.name).mkString(",")} to ${packet.nextHop.name} with pcp ${packet.pcp}")
      queue.addPacket(packet)
    } else {
      throw new IllegalAccessException("queue was not empty")
    }
  }
  
  def removePackage(packet: Packet): Any = {
    val nextHops = packet.target.filter(packet.nextHop != _).map(t => Network.routingTable(packet.nextHop)(t))
    val sameNextHop = nextHops.forall(_ == nextHops.head)
    if (sameNextHop) {
      if (packet.nextHop != packet.target.head) {
        val nextHop = Network.routingTable(packet.nextHop)(packet.target.head)
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, packet.target, packet.pcp))
      }
      else {
        Console.println("package arrived!!!")
      }
      Console.println(s"successfully sent package with target ${packet.target.map(_.name).mkString(",")} to ${packet.nextHop.name} with pcp ${packet.pcp}")
      queue.removePacket(packet)
    }
    else {
      packet.target.foreach(target => {
        val nextHop = Network.routingTable(packet.nextHop)(target)
        packet.nextHop.addToQueue(Packet(packet.size, nextHop, List(target), packet.pcp))
        Console.println(s"successfully sent package with target ${packet.target.map(_.name).mkString(",")} to ${packet.nextHop.name} with pcp ${packet.pcp}")
        queue.removePacket(packet)
      })
    }
  }

}
