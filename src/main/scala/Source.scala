trait Source {
  def getAvailableFrame(time: Long, tick: Long, node: Node): Option[Packet]
}