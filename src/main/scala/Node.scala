trait Node {

  def getQueue: Queue
  def name: String
  def tick(tick: Long, availabilites: (lnode: Node, rnode: Node) => Option[Connection]): Unit
  def prepareTick(tick: Long): Unit

}
