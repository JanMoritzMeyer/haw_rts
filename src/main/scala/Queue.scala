trait Queue {
  def addPacket(packet: Packet): Unit
  def removePacket(packet: Packet): Unit
  def isEmpty: Boolean
  def getQueue(time: Double): List[Packet]
  def sending(ns: Double, pcp: Int): Unit
  def notSending(ns: Double): Unit
  def canSend(pcp: Int, time: Double): Boolean
}
