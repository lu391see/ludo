package de.htwg.se.ludo.model.diceComponent.dice8Impl

import de.htwg.se.ludo.model.diceComponent._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DiceSpec extends AnyWordSpec with Matchers {
  "A 8 sided Dice " when {
    val dice = new dice8Impl.Dice()
    "thrown" should {
      val diceValue = dice.throwing
      "have a maximum pip amount of 8" in {
        dice.pips should be (8)
      }
      "and show a value between 1 and 8" in {
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6|7|8"
      }
    }
  }
}
