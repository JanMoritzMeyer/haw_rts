package control

class TAS(start: Double, schedule: Seq[(Double, Boolean)]) extends Gate {

  var simFrom: Double = 0
  var simTo: Double = start
  var cycle: Double = 0
  var open = false

  override def canSend(time: Double): Boolean = {
    while (simTo <= time) {
      val (period, state) = schedule(cycle.toInt)
      cycle += 1
      cycle = cycle % schedule.size
      simFrom = simTo
      simTo += period
      open = state
    }
    open
    
  }

  override def sending(ns: Double): Unit = {}

  override def notSending(ns: Double): Unit = {}

  override def reset(): Unit = {}
}
