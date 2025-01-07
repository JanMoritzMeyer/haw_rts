import control.{Gate, OpenGate}

case class Connection(lnode: Node, lport: Int, rnode: Node, rport: Int, speed: Int){

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
    queue.removePacket(packet)
  }

}
