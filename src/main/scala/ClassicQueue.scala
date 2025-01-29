import control.Gate

class ClassicQueue (prioritizer: Map[Int, Gate]) extends Queue {

  private var queue: List[Packet] = List.empty

  def addPacket(packet: Packet): Unit = {
    queue = queue :+ packet
  }

  def removePacket(packet: Packet): Unit = {
    queue = queue.filterNot(_ == packet)
  }

  def isEmpty: Boolean = queue.isEmpty

  def getQueue(time: Double): List[Packet] = {
    val availablePrios = queue
      .groupBy(_.pcp)
      .keys
      .toList
    prioritizer
      .foreach(x => {
        val (prio, gate) = x
        if (!availablePrios.contains(prio)) {
          gate.reset()
        }
      })
    queue
      .filter(x => canSend(x.pcp, time))
      .sortBy(_.pcp)
  }

  def sending(ns: Double, pcp: Int): Unit = {
    prioritizer.get(pcp).foreach(_.sending(ns))
    prioritizer.filter(_._1 != pcp).foreach(_._2.notSending(ns))
  }

  def notSending(ns: Double): Unit = prioritizer.foreach(_._2.notSending(ns))

  def canSend(pcp: Int, time: Double): Boolean = prioritizer.get(pcp).forall(_.canSend(time))

}