class Network (nodes: List[Node], connections: List[Connection]) {
  
  val indexedConnections: Map[(Node, Node), Connection] = {
    connections
      .flatMap(connection => {
        List((connection.lnode, connection.rnode) -> connection, (connection.rnode, connection.lnode) -> connection)
      })
      .toMap
  }
  
  def checkAvailability(lnode: Node, rnode: Node): Option[Connection] = {
    indexedConnections.get((lnode, rnode))
      .filter(_.isAvailable)
  }

  def tick(tick: Long, time: Int): Unit = {
    
    EventController.getSortedEvents
      .filter(time == _.endTime)
      .foreach(_.triggerCallback())
    
    nodes
      .foreach(_.prepareTick(tick))
    
    nodes
      .foreach(_.tick(tick, checkAvailability))
    
  }
}
