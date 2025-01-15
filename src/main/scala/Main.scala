import control.{CBS, OpenGate}

import scala.language.postfixOps

  @main def main (): Unit = {

    // Time: MS * 1000 -> NS
    var clock: Int = 0
    //val endTime: Double = Converter.ms_to_ns(1_000_0)
    val endTime: Double = 7_228_000.0

    val s1 = Frame("s1", 0, 300, 300)
    val s2 = Frame("s2", 0, 300, 300)
    val s3 = Frame("s3", 5, 300, 300)

    val ho1 = Host("ho1", Queue(OpenGate()))
    val ho2 = Host("ho2", Queue(OpenGate()))
    val ho3 = Host("ho3", Queue(OpenGate()))
    val ho4 = Host("ho4", Queue(OpenGate()))
    val sw1 = Switch("sw1")

    ho1.addTrafficSource(TrafficSource(Converter.ms_to_ns(3), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), s1, List(ho4)))
    ho2.addTrafficSource(TrafficSource(Converter.ms_to_ns(4), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), s2, List(ho4)))
    ho3.addTrafficSource(TrafficSource(Converter.ms_to_ns(5), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), s3, List(ho4)))

    val connection = Connection(ho1, 0, sw1, 0, Converter.mbits_to_bytens(100))
    val connection1 = Connection(ho2, 0, sw1, 0, Converter.mbits_to_bytens(100))
    val connection2 = Connection(ho3, 0, sw1, 0, Converter.mbits_to_bytens(100))
    val connection3 = Connection(sw1, 0, ho4, 0, Converter.mbits_to_bytens(100))

    val network = Network(List(ho1, ho2, ho3, ho4, sw1), List(connection, connection1, connection2, connection3))
    
    while (endTime > clock) {
      GlobalTime.time = clock
      network.tick(1, clock)
      clock = clock + 1
    }


}