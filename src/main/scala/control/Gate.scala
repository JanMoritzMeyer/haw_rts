package control

trait Gate {

  def getPriority: Double
  def sending(ns: Double): Unit
  def blocked(ns: Double): Unit
  def notSending(): Unit

}
