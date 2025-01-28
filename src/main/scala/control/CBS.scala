package control

case class CBS (idleSlope: Double, sendSlope: Double) extends Gate {

  var credit = 0.0

  override def canSend(time: Double): Boolean = credit >= 0

  override def sending(ns: Double): Unit = credit = credit - sendSlope * ns

  override def notSending(ns: Double): Unit = credit = credit + idleSlope * ns

  override def reset(): Unit = credit = 0
}
