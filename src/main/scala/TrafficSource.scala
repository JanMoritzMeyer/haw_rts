import scala.util.Random

case class TrafficSource(start: Double, end: Double, delta_min: Double, delta_max: Double, frame: Frame, target: Node) {

  var nextSend: Double = start

  def getAvailableFrame(time: Long, node: Node): Option[Packet] = {
    if (time == nextSend) {
      println("delta_min: " + delta_min + " delta_max: " + delta_max)
      if (delta_min.equals(delta_max)) {
        nextSend = nextSend + delta_min
      } else {
        nextSend = nextSend + Random.between(delta_min, delta_max)
      }
      return Some(Packet(frame.getSize(), Network.routingTable(node)(target), target, frame.pcp))
    }
    None
  }

}