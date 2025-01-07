import control.OpenGate

case class Switch(name: String) extends Node {

  val queue = new Queue(OpenGate())
  
  override def getQueue: Queue = queue

  override def tick(tick: Long, availabilites: (lnode: Node, rnode: Node) => Option[Connection]): Unit = {
    
  }

  override def prepareTick(tick: Long, time: Long): Unit = {
    
  }
}