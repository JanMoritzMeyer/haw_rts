import control.{CBS, OpenGate}

import scala.language.postfixOps

  @main def main (): Unit = {

    // Time: MS * 1000 -> NS
    var clock: Int = 0
    val endTime: Double = Converter.ms_to_ns(1_000)

    val frame = Frame("s1", 0, 117, 117)

    val node1 = Host("ho1", Queue(OpenGate()))
    val node2 = Host("ho2", Queue(OpenGate()))
    val switch1 = Switch("s1")

    node1.addTrafficSource(TrafficSource(Converter.ms_to_ns(5), Converter.ms_to_ns(1_000), Converter.ms_to_ns(10), Converter.ms_to_ns(10), frame, node2))

    val connection = Connection(node1, 0, switch1, 0, Converter.mbits_to_bytens(100))
    val connection2 = Connection(switch1, 0, node2, 1, Converter.mbits_to_bytens(100))

    val network = Network(List(node1, node2, switch1), List(connection, connection2))
    
    while (endTime > clock) {
      network.tick(1, clock)
      Console.println(clock)
      clock = clock + 1
    }


}