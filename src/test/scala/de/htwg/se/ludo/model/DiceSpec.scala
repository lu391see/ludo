package de.htwg.se.ludo.model
import de.htwg.se.ludo.model.diceComponent._

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers{
  "A 6 sided Dice " when {
    "thrown" should {
      val dice = dice6Impl.Dice()
      "have a value between 1 and 6" in {
        val diceValue = dice.pips
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6"
      }
    }
  }

  "A 8 sided Dice " when {
    "thrown" should {
      val dice = dice8Impl.Dice()
      "have a value between 1 and 6" in {
        val diceValue = dice.pips
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6|7|8"
      }
    }
  }
}
