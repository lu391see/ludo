package de.htwg.se.ludo.model.diceComponent.dice8Impl

import de.htwg.se.ludo.model.diceComponent._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DiceSpec extends AnyWordSpec with Matchers {
  "A 8 sided Dice " when {
    "thrown" should {
      val dice = dice8Impl.Dice()
      "have a value between 1 and 8" in {
        val diceValue = dice.pips
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6|7|8"
      }
    }
  }
}
