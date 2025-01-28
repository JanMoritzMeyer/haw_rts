package control

class OpenGate extends Gate {
  override def canSend(time: Double): Boolean = true

  override def sending(ns: Double): Unit = {}

  override def notSending(ns: Double): Unit = {}

  override def reset(): Unit = {
    
  }
}
