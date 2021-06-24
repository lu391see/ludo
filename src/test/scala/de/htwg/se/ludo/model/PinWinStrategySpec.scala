package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.playerComponent.{Player, Team}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

class PinWinStrategySpec extends AnyWordSpec with Matchers {
  "A WinStrategy" when {
    val player = Player("dummy", new Team(Color.green,0,4,42))
    "set to OnePin" should {
      val strategy = OnePinWinStrategy()
      "return false if no pin is at the players home field" in {
        player.move(0,12)
        strategy.hasWon(player) should be (false)
      }
      "return true when at least one pin is at the players home field" in {
        player.finish(1)
        strategy.hasWon(player) should be (true)
        player.finish(3)
        strategy.hasWon(player) should be (true)
      }

    }
    "set to AllPin" should {
      val strategy = AllPinWinStrategy()
      "return true if all pins are in the home field" in {
        player.finish(0)
        player.finish(1)
        player.finish(2)
        player.finish(3)
        strategy.hasWon(player) should be (true)

      }
      "return false as long as at least one pin didn't reach home" in {
        player.move(0, 7)
        strategy.hasWon(player) should be (false)
      }
    }
  }
}
