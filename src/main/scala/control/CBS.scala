package control

case class CBS (idleSlope: Double, sendSlope: Double) extends Gate {

  var credit = 0.0

  override def canSend: Boolean = credit >= 0

  override def sending(ns: Double): Unit = credit = credit - sendSlope * ns

  override def notSending(ns: Double): Unit = credit = {
    if (canSend) {
      0.0
    } else {
      credit + idleSlope * ns
    }
  }
}
