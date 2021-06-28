package de.htwg.se.ludo.model.playerComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class PinSpec extends AnyWordSpec with Matchers{
  "A Pin" when {
    "having a name like Y1" should {
      val pinY1 = Pin("Y1")
      "return its id" in {
        pinY1.number should be(1)
      }
      "be based at base position + index" in {
        pinY1.base(0).position should be(0)
      }
    }
    "set to Y2, 1" should {
      val pinY2 = Pin("Y2", 1)
      "have position" in {
        pinY2.position should be(1)
      }
      "change position after add" in {
        pinY2.move(10).position should be(10)
      }
      "be based at base position + index" in {
        pinY2.base(0).position should be(1)
      }
    }
  }
}
