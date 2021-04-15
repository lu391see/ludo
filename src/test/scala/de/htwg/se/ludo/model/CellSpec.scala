package de.htwg.se.ludo.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
// import org.scalatest.{Matchers, WordSpec}

class CellSpec extends AnyWordSpec with Matchers{
  "A Cell" when { "not set to any value " should{
    val emptyCell = Cell(0)
    "have value 0" in {
      emptyCell.value should be(0)
    }
    "not be set" in {
      emptyCell.isSet should be(false)
    }
    "show unplayable mark" in {
      emptyCell.toString.matches("/s{3}")
    }
  }
  "set to a specific value" should {
    val nonEmptyCell = Cell(5)
    "return that value" in {
      nonEmptyCell.value should be(5)
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
