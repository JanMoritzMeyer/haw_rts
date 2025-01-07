trait Node {

  def getQueue: Queue
  def name: String
  def tick(tick: Long, availabilites: (Node, Node) => Option[Connection]): Unit
  def prepareTick(tick: Long): Unit

}
