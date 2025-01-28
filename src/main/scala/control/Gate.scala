package control

trait Gate {

  def canSend: Boolean
  def sending(ns: Double): Unit
  def notSending(ns: Double): Unit
  def reset(): Unit

}
