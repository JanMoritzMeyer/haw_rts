package control

class OpenGate extends Gate {
  override def canSend: Boolean = true

  override def sending(ns: Double): Unit = {}

  override def notSending(ns: Double): Unit = {}

}
