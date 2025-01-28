import control.{CBS, OpenGate}

import scala.language.postfixOps

  @main def main (): Unit = {

    // Time: MS * 1000 -> NS
    var clock: Int = 0
    val endTime: Double = Converter.ms_to_ns(5_000)

    val s1 = Frame("s1", 0, 500, 500)

    val ho1 = Host("ho1", Queue(Map(0 -> CBS(32, 68))))
    val ho2 = Host("ho2", Queue(Map.empty))
    val sw1 = Switch("sw1")

    ho1.addTrafficSource(TrafficSource(Converter.ms_to_ns(5), Converter.ms_to_ns(1_000), Converter.ms_to_ns(125), Converter.ms_to_ns(125), s1, List(ho2)))

    val connection = Connection(ho1, 0, sw1, 0, Converter.mbits_to_bytens(100))
    val connection1 = Connection(ho2, 1, sw1, 0, Converter.mbits_to_bytens(100))

    val network = Network(List(ho1, ho2, sw1), List(connection, connection1))
    
    while (endTime > clock) {
      GlobalTime.time = clock
      network.tick(100, clock)
      clock = clock + 100
    }


}