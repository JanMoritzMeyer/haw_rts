package control

trait Gate {

  def canSend: Boolean
  def sending(ns: Double): Unit
  def blocked(ns: Double): Unit
  def notSending(): Unit

}
