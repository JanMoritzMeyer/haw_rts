package control

case class CBS (idleSlope: Double, sendSlope: Double) extends Gate {

  var credit = 0.0

  override def getPriority: Double = credit

  override def sending(ns: Double): Unit = credit = credit - sendSlope * ns

  override def blocked(ns: Double): Unit = credit = credit + idleSlope * ns

  override def notSending(): Unit = credit = 0
}
