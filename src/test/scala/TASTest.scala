import control.{CBS, TAS}
import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.should

import collection.mutable.Stack
import org.scalatest.*
import flatspec.*
import matchers.*

class TASTest extends AnyFlatSpec with should.Matchers {

  "TAS" should "be open before start time" in {
    val tas = TAS(50, Seq.empty)

    tas.canSend(10) shouldBe false
  }
  "TAS" should "be closed at start time" in {
    val tas_schedule = Seq(
      (10.0, true),
      (50.0, false),
      (10.0, false),
      (30.0, false),
    )
    val tas = TAS(50, tas_schedule)

    tas.canSend(50) shouldBe true
    tas.canSend(59) shouldBe true
    tas.canSend(60) shouldBe false
    tas.canSend(149) shouldBe false
    tas.canSend(150) shouldBe true
  }
}
