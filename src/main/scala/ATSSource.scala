import UID.getUnique

import scala.util.Random

class ATSSource(
               start: Double,
               end: Double,
               delta_min: Double,
               delta_max: Double,
               frame: Frame,
               target: List[Node],
               committed_information_rate: Double,
               committed_burst_size: Double,
               max_residence_time: Double
               ) extends Source {

  private var nextCreation: Double = start

  private var queue: Map[Double, Packet] = Map.empty

  private var lastTime: Double = -100_000 // the bucket is full in the beginning

  def calculateEligibleTime(time: Long, packet: Packet): Double = {
    val currentMax: Double = if(queue.nonEmpty) {
      queue.keys.max
    } else {
      0
    }
    val finishedTime = lastTime + packet.size / committed_information_rate
    Seq(time.toDouble, finishedTime, currentMax).max
  }

  def getAvailableFrame(time: Long, tick: Long, node: Node): Option[Packet] = {
    if (time == nextCreation && time < end) {
      val nextHops = target.map(t => Network.routingTable(node)(t))
      if (delta_min.equals(delta_max)) {
        nextCreation = nextCreation + delta_min
      } else {
        nextCreation = nextCreation + Random.between(delta_min, delta_max)
      }
      val p = Packet(frame.getSize(), nextHops.head, target, frame.pcp, frame.stream, getUnique)
      val eTime = calculateEligibleTime(time, p)
      if (eTime <= time+max_residence_time) {
        queue = queue.updated(eTime, p)
        lastTime = eTime
      } else {
        Console.println(s"threw away package ${p.uid}")
      }
    }
    if (time == 10_000) {
      Console.println("blub")
    }
    val pOpt = queue
      .filter(_._1 <= time)
      .values
      .headOption
    pOpt
      .foreach(x => {
        queue = queue.filter(_._2 != x)
      })
    pOpt
  }
}
