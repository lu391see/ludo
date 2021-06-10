package de.htwg.se.ludo.model
import de.htwg.se.ludo.model.diceComponent.dice6Impl.Dice

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class DiceSpec extends AnyWordSpec with Matchers{
  "A Dice " when {
    "thrown" should {
      val dice = Dice()
      "have a value between 1 and 6" in {
        val diceValue = dice.pips
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6"
      }
    }
  }
}
