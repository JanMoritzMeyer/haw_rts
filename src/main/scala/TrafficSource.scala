case class TrafficSource(start: Int, end: Int, delta: Int, frame: Frame, target: Node) {

  def getAvailableFrame: Option[Packet] = {
    Some(Packet(frame.getSize, target, target, frame.pcp))
  }

  def frameSent(): Unit = {

  }

}
