package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

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
      "and return the correpsonding color" in {
        nonEmptyCell.colorFromChar should be(Color.yellow)
      }
      "show the pin number at this position" in {
        nonEmptyCell.pinNumber should be(1)
      }

      "show number in String" in {
        nonEmptyCell.toString.matches("/s5/s")
      }
      "represent only the for main colors or neutral white" in {
        Cell("R4").colorFromChar should be (Color.red)
        Cell("B3").colorFromChar should be (Color.black)
        Cell("Y2").colorFromChar should be (Color.yellow)
        Cell("G1").colorFromChar should be (Color.green)
        Cell("O4").colorFromChar should be (Color.white)
      }
    }

  }
}
