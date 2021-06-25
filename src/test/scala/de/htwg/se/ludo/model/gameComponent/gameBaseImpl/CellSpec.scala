package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class CellSpec extends AnyWordSpec with Matchers {
  "A Cell" when {
    "not set to any value " should {
      val emptyCell = Cell("")
      "not be set" in {
        emptyCell.isSet should be(false)
      }
      "show unplayable mark" in {
        emptyCell.toString.matches("/s{3}")
      }
    }
    "set to a specific value" should {
      val nonEmptyCell = Cell("Y1")
      "return that value" in {
        nonEmptyCell.value should be("Y1")
      }
      "be set" in {
        nonEmptyCell.isSet should be(true)
      }
      "show the pin color at this position as char" in {
        nonEmptyCell.color should be('Y')
      }
      "show the pin number at this position" in {
        nonEmptyCell.pinNumber should be(1)
      }

      "show number in String" in {
        nonEmptyCell.toString.matches("/s5/s")
      }
    }
  }
}
