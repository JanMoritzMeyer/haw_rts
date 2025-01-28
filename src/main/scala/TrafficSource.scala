import UID.getUnique

import scala.util.Random

case class TrafficSource(start: Double, end: Double, delta_min: Double, delta_max: Double, frame: Frame, target: List[Node]) extends Source {

  private var nextSend: Double = start

  def getAvailableFrame(time: Long, tick: Long, node: Node): Option[Packet] = {
    if (time > end) {
      return None
    }
    if (time == nextSend) {
      val nextHops = target.flatMap(t => Network.routingTable(node)(t))
      if (delta_min.equals(delta_max)) {
        nextSend = nextSend + delta_min
      } else {
        nextSend = nextSend + Random.between(delta_min, delta_max)
      }
      return Some(Packet(frame.getSize(), nextHops.head, target, frame.pcp, frame.stream, getUnique))
    }
    None
  }

}
