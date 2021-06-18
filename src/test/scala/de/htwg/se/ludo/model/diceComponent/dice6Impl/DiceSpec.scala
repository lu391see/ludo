package de.htwg.se.ludo.model.diceComponent.dice6Impl

import de.htwg.se.ludo.model.diceComponent._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DiceSpec extends AnyWordSpec with Matchers{
  "A 6 sided Dice " when {
    val dice = new dice6Impl.Dice()
    "thrown" should {
      val diceValue = dice.throwing
      "have a maximum pip amount of 6" in {
        dice.pips should be (6)
      }
      "and show a value between 1 and 6" in {
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6"
      }
    }
  }
}
