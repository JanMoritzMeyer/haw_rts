import scala.util.Random

case class TrafficSource(start: Int, end: Int, delta_min: Int, delta_max: Int, frame: Frame, target: Node) {

  var nextSend: Int = start

  def getAvailableFrame(time: Long): Option[Packet] = {
    if (time == nextSend) {
      nextSend = nextSend + Random.between(delta_min, delta_max)
      return Some(Packet(frame.getSize, target, target, frame.pcp))
    }
    None
  }

}
