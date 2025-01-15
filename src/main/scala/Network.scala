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

  /*
  this function returns all nodes that are reachable from the startPoint
   */
  def getConnectionsFor(node: Node): List[Node] = getConnectionsForRec(node, List(node))

  def getConnectionsForRec(node: Node, visited: List[Node]): List[Node] = simpleConnections
    .get(node)
    .map(nodes => {
      val filteredNodes: List[Node] = nodes
        .filter(!visited.contains(_))
      filteredNodes match
        case Nil => List.empty
        case some => some ++ some.flatMap(node => {
          getConnectionsForRec(node, visited :+ node)
        })
    })
    .map(_.distinct)
    .getOrElse(List.empty)

  val routingTable: Map[Node, Map[Node, Node]] = nodes  // (aktuelle Node, (Ziel Node, Next Hop))
    .map(node => {
      val possibleConnections = simpleConnections.getOrElse(node, List.empty)
      val obvConnections = possibleConnections
        .map(x => (x, x))
      val pairs = possibleConnections
        .flatMap(connection => {
          getConnectionsFor(connection).filter(_ != node).map(reachable => {
            (reachable, connection)
          })
        })
        .toMap
      (node -> (pairs ++ obvConnections))
    })
    .toMap


  Network.routingTable = routingTable


  def tick(tick: Long, time: Int): Unit = {

    if (time == 14360) {
      Console.println("blub")
    }

    EventController.getSortedEvents
      .filter(!_.isDone)
      .filter(x => (time >= x.endTime && (time-1) < x.endTime))
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