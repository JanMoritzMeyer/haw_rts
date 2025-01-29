import control.CBS
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import collection.mutable.Stack
import org.scalatest._
import flatspec._
import matchers._

class NetworkTest extends AnyFlatSpec with should.Matchers {

  "Network" should "get two routes if available" in {
    val ho1 = Host("ho1", ClassicQueue(Map.empty))
    val ho2 = Host("ho2", ClassicQueue(Map.empty))
    val ho3 = Host("ho3", ClassicQueue(Map.empty))
    val s1 = Switch("s1")
    val s2 = Switch("s2")

    val c1 = Connection(ho1, 0, s1, 0, 100)
    val c2 = Connection(ho1, 1, s2, 0, 100)
    val c3 = Connection(s1, 1, ho2, 0, 100)
    val c4 = Connection(s1, 2, ho3, 0, 100)
    val c5 = Connection(s2, 1, ho2, 1, 100)

    val network = Network(List(ho1, ho2, ho3, s1, s2), List(c1, c2, c3, c4, c5))

    network.createRoutingTable(ho1)(ho2) should be(List(s1, s2))
    network.createRoutingTable(ho1)(s1) should be(List(s1))
    network.createRoutingTable(ho1)(ho3) should be(List(s1))
  }

  "Network" should "create routing table in complex network" in {
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

    val network = Network(List(ho1, ho2, sw0, sw1, sw2, sw3, sw4), List(
      connection,
      connection1,
      connection2,
      connection3,
      connection4,
      connection5,
      connection6,
      connection7,
    ))

    Network.routingTable(ho1)(ho2) shouldBe List(sw0)
    Network.routingTable(sw0)(ho2) shouldBe List(sw1, sw2, sw3)
    Network.routingTable(sw1)(ho2) shouldBe List(sw4)
    Network.routingTable(sw2)(ho2) shouldBe List(sw4)
    Network.routingTable(sw3)(ho2) shouldBe List(sw4)
    Network.routingTable(sw4)(ho2) shouldBe List(ho2)
  }



}
