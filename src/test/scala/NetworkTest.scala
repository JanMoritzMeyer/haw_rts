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



}
