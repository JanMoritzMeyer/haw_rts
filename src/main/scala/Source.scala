trait Source {
  def getAvailableFrame(time: Long, node: Node): Option[Packet]
}