package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.Cell
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class CellSpec extends AnyWordSpec with Matchers{
  "A Cell" when { "not set to any value " should{
    val emptyCell = Cell("")
    "have empty value" in {
      emptyCell.getValue should be("")
    }
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
      nonEmptyCell.getValue should be("Y1")
    }
    "be set" in {
      nonEmptyCell.isSet should be(true)
    }
    "number should be String" in {
      nonEmptyCell.toString.isInstanceOf[String] should be(true)
    }

    "show number in String" in {
      nonEmptyCell.toString.matches("/s5/s")
    }
  }
  }
}
