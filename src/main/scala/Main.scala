import control.{CBS, OpenGate, TAS}

import scala.language.postfixOps

  @main def main (): Unit = {

    // Time: MS * 1000 -> NS
    var clock: Int = 0
    val endTime: Double = Converter.ms_to_ns(5_000)

    val s1 = Frame("s1", 0, 100, 100)

    val ho1 = Host("ho1", ClassicQueue(Map.empty))
    val ho2 = Host("ho2", ClassicQueue(Map.empty))

    val sw0 = Switch("sw0", Map.empty)
    val sw1 = Switch("sw1", Map.empty)
    val sw2 = Switch("sw2", Map.empty)
    val sw3 = Switch("sw3", Map.empty)
    val sw4 = Switch("sw4", Map.empty)

    val connection = Connection(ho1, 0, sw0, 0, Converter.mbits_to_bytens(100))
    val connection1 = Connection(sw0, 1, sw1, 0, Converter.mbits_to_bytens(100))
    val connection2 = Connection(sw0, 2, sw2, 0, Converter.mbits_to_bytens(100))
    val connection3 = Connection(sw0, 3, sw3, 0, Converter.mbits_to_bytens(100))
    val connection4 = Connection(sw1, 1, sw4, 1, Converter.mbits_to_bytens(100))
    val connection5 = Connection(sw2, 1, sw4, 2, Converter.mbits_to_bytens(100))
    val connection6 = Connection(sw3, 1, sw4, 3, Converter.mbits_to_bytens(100))
    val connection7 = Connection(sw4, 1, ho2, 0, Converter.mbits_to_bytens(100))

    ho1.addTrafficSource(ATSSource(Converter.ms_to_ns(5), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), s1, List(ho2), Converter.mbits_to_bytens(80), 800, Converter.ms_to_ns(200)))

    val network = Network(List(ho1, ho2, sw1, sw2, sw3, sw4), List(connection,
      connection1,
      connection2,
      connection3,
      connection4,
      connection5,
      connection6,
      connection7,
    ))
    
    while (endTime > clock) {
      GlobalTime.time = clock
      network.tick(100, clock)
      clock = clock + 100
    }


}