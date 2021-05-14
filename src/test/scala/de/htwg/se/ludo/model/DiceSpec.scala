package de.htwg.se.ludo.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
// import org.scalatest.{Matchers, WordSpec}

class DiceSpec extends AnyWordSpec with Matchers{
  "A Dice " when {
    "thrown" should {
      val dice = RandomDice()
      "have a value between 1 and 6" in {
        val diceValue = dice.pips
        diceValue.isInstanceOf[Int]
        diceValue.toString matches "1|2|3|4|5|6"
      }
    }
  }
}
