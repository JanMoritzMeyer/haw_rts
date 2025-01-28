import control.{CBS, OpenGate, TAS}

import scala.language.postfixOps

  @main def main (): Unit = {

    // Time: MS * 1000 -> NS
    var clock: Int = 0
    val endTime: Double = Converter.ms_to_ns(5_000)

    val s1 = Frame("s1", 0, 100, 100)

    val ho1 = Host("ho1", ClassicQueue(Map.empty))
    val ho2 = Host("ho2", ClassicQueue(Map.empty))

    val sw1 = Switch("sw1", Map.empty)

    val connection = Connection(ho1, 0, sw1, 0, Converter.mbits_to_bytens(100))
    val connection1 = Connection(ho2, 1, sw1, 0, Converter.mbits_to_bytens(100))

    ho1.addTrafficSource(ATSSource(Converter.ms_to_ns(5), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), s1, List(ho2), Converter.mbits_to_bytens(80), 800, Converter.ms_to_ns(200)))

    val network = Network(List(ho1, ho2, sw1), List(connection, connection1))
    
    while (endTime > clock) {
      GlobalTime.time = clock
      network.tick(100, clock)
      clock = clock + 100
    }


}