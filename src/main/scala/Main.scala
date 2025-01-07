import control.CBS

import scala.language.postfixOps

object Main {

  @main def main (): Unit = {

    var clock: Int = 0
    val endTime: Int = 100

    val frame = Frame("s1", 0, 100, 300)
    val frame2 = Frame("s1", 1, 100, 300)


    val node1 = Host("ho1", Queue(CBS(100, 100)))
    val node2 = Host("ho2", Queue(CBS(100, 100)))
    
    node1.addTrafficSource(TrafficSource(0, 100, 20, frame, node2))
    node1.addTrafficSource(TrafficSource(0, 100, 20, frame2, node2))

    val connection = Connection(node1, 0, node2, 0, 100)
    
    val network = Network(List(node1, node2), List(connection))

    while (endTime > clock) {
      network.tick(1, clock)
      Console.println(clock)
      clock = clock + 1
    }

  }
}