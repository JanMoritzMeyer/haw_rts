package control

class OpenGate extends Gate {
  override def getPriority: Double = 0.0

  override def sending(ns: Double): Unit = {}

  override def blocked(ns: Double): Unit = {}

  override def notSending(): Unit = {}
}
