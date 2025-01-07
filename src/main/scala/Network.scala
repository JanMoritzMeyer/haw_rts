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

  val simpleConnections: Map[Node, List[Node]] = connections
    .flatMap(c => List((c.lnode -> c.rnode), (c.rnode, c.lnode)))
    .groupMap(_._1)(_._2)

  def getRoutesFor(node: Node, startPoint: Node): List[Node] = simpleConnections
    .get(node)
    .map(nodes => {
      nodes ++ nodes
        .filter(_ != startPoint)
        .map(getRoutesFor(_, node))
        .fold(List.empty)((agg, x) => agg.toList ++ x.toList)
    })
    .map(_.distinct)
    .getOrElse(List.empty)

  val routingTable: Map[Node, Map[Node,Node]] = nodes
    .map(node => {
      val possibleConnections = simpleConnections(node)
      val simpleTable: Map[Node, Node] = possibleConnections.flatMap(p => {
          val availableNodes = getRoutesFor(p, p)
            .map(n => n -> p)
            .toMap
          availableNodes
        })
        .toMap
      node -> simpleTable
    })
    .toMap
  
  Network.routingTable = routingTable


  def tick(tick: Long, time: Int): Unit = {
    
    EventController.getSortedEvents
      .filter(time == _.endTime)
      .foreach(_.triggerCallback())
    
    nodes
      .foreach(_.prepareTick(tick, time))
    
    nodes
      .foreach(_.tick(tick, time, checkAvailability))
    
  }
}

object Network {
  var routingTable: Map[Node, Map[Node, Node]] = Map.empty
}