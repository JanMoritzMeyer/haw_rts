import control.Gate

class Queue (prioritizer: Map[Int, Gate]) {
  
  var queue: List[Packet] = List.empty
  
  def addPacket(packet: Packet): Any = {
    queue = queue :+ packet
  }
  
  def removePacket(packet: Packet): Any = {
    queue = queue.filter(_ != packet)
  }
  
  def isEmpty: Boolean = queue.isEmpty
  
  def getQueue: List[Packet] = {
    val availablePrios = queue
      .groupBy(_.pcp).keys
      .toList
    prioritizer
      .foreach(x => {
        val (prio, gate) = x
        if (!availablePrios.contains(prio)) {
          gate.reset()
        }
      })
    queue
      .filter(x => canSend(x.pcp))
      .sortBy(_.pcp)
  }
  
  def sending(ns: Double, pcp: Int): Unit = {
    prioritizer.get(pcp).foreach(_.sending(ns))
    prioritizer.filter(_._1 != pcp).foreach(_._2.notSending(ns))
  }

  def notSending(ns: Double): Unit = prioritizer.foreach(_._2.notSending(ns))

  def canSend(pcp: Int): Boolean = prioritizer.get(pcp).forall(_.canSend)

}
