package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.playerComponent.Pin

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PinSpec extends AnyWordSpec with Matchers{
  "A Pin" when {
    "set to Y1, 0" should {
      val pinY1 = Pin("Y1", 0)
      "have position" in {
        pinY1.position should be(0)
      }
      "change position after add" in {
        pinY1.move(10).position should be(10)
      }
    }
  }
}
