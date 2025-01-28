class Network (nodes: List[Node], connections: List[Connection]) {
  
  val indexedConnections: Map[(Node, Node), Connection] = {
    connections
      .flatMap(connection => {
        List((connection.lnode, connection.rnode) -> connection)
      })
      .toMap
  }
  
  def checkAvailability(lnode: Node, rnode: Node): Option[Connection] = {
    indexedConnections.get((lnode, rnode))
      .filter(_.isAvailable)
  }

  val simpleConnections: Map[Node, List[Node]] = connections
    .flatMap(c => List(c.lnode -> c.rnode))
    .groupMap(_._1)(_._2)

  def getAllConnectionsFor(node: Node, exclude: Seq[Node] = Seq.empty): Seq[Node] = {
    val found = simpleConnections
      .getOrElse(node, Seq.empty)
      .filterNot(exclude.contains(_))
    (found ++ found.flatMap(getAllConnectionsFor(_, exclude :+ node)))
      .distinct
      .sortBy(_.name)
  }

  def createRoutingTable: Map[Node, Map[Node, Seq[Node]]] = {
    nodes
      .map(node => {
        node -> simpleConnections
          .getOrElse(node, List.empty)
          .map(c => c -> (getAllConnectionsFor(c, Seq(node)) :+ c))
          .flatMap(pair => {
            val (start, reachable) = pair
            reachable
              .map(r => (r, start))
              .toList
          })
          .groupMap(_._1)(_._2)
      })
      .toMap
  }
  
  Network.routingTable = createRoutingTable

  def tick(tick: Long, time: Int): Unit = {

    EventController.getSortedEvents
      .filter(!_.isDone)
      .filter(_.endTime == time)
      .foreach(_.triggerCallback())
    
    nodes
      .foreach(_.prepareTick(tick, time))
    
    nodes
      .foreach(_.tick(tick, time, checkAvailability))
    
  }
}

object Network {
  var routingTable: Map[Node, Map[Node, Seq[Node]]] = Map.empty
}