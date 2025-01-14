import control.CBS
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

class NetworkTest extends AnyFlatSpec with should.Matchers {

  "Network" should "get routes" in {
      val node1 = Host("ho1", Queue(CBS(100, 100)))
      val node2 = Host("ho2", Queue(CBS(100, 100)))
      val switch1 = Switch("s1")

      val connection = Connection(node1, 0, switch1, 0, 100)
      val connection2 = Connection(node2, 0, switch1, 0, 100)

      val network = Network(List(node1, node2, switch1), List(connection, connection2))

      val routes = network.getConnectionsFor(node1)

      val routessw1 = network.getConnectionsFor(switch1)

      routes shouldBe List(switch1, node2)
      routessw1 shouldBe List(node1, node2)
    }

  "Network" should "build correct Routing Table" in {
    val node1 = Host("ho1", Queue(CBS(100, 100)))
    val node2 = Host("ho2", Queue(CBS(100, 100)))
    val switch1 = Switch("s1")

    val connection = Connection(node1, 0, switch1, 0, 100)
    val connection2 = Connection(node2, 0, switch1, 0, 100)

    val network = Network(List(node1, node2, switch1), List(connection, connection2))

    Network.routingTable.get(node1) shouldBe Option(Map(
      node2 -> switch1,
      switch1 -> switch1
    ))

  }



}
