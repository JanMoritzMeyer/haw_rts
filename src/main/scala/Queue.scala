import control.Gate

class Queue (prioritizer: Gate) {
  
  var queue: List[Packet] = List.empty
  
  def addPacket(packet: Packet): Any = {
    queue = queue :+ packet
  }
  
  def removePacket(packet: Packet): Any = {
    queue = queue.filter(_ != packet)
  }
  
  def isEmpty: Boolean = queue.isEmpty
  
  def getQueue: List[Packet] = queue.sortBy(_.pcp)

  def sending(ns: Double) = prioritizer.sending(ns)

  def notSending(ns: Double): Unit = prioritizer.notSending(ns)
  
  def canSend(): Boolean = prioritizer.canSend

}
