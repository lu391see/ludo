package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.{Board, Cell}
import de.htwg.se.ludo.model.playerComponent.{Player, Team}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

class PinWinStrategySpec extends AnyWordSpec with Matchers {
  "A WinStrategy" when {
    val player = Player("dummy", new Team(Color.green,0,4,6))
    val board = new Board(10, Cell(""), 4)
    "set to OnePin" should {
      val strategy = OnePinWinStrategy()
      "return false if no pin is at the players home field" in {
        val b1 = board.replaceCell(5, Cell("G1"))
        strategy.hasWon(player, b1) should be (false)
      }
      "return true when at least one pin is at the players home field" in {
        val b2 = board.replaceCell(6, Cell("G1"))
        strategy.hasWon(player, b2) should be (true)
        val b3 = b2.replaceCell(6, Cell("G1"))
        strategy.hasWon(player, b3) should be (true)
      }
    }
    "set to AllPin" should {
      val strategy = AllPinWinStrategy()
      val b4 = board.replaceCell(6, Cell("G1"))
        .replaceCell(7, Cell("G2"))
        .replaceCell(8, Cell("G3"))
        .replaceCell(9, Cell("G4"))
      "return true if all pins are in the home field" in {
        strategy.hasWon(player, b4) should be (true)
      }
      "return false as long as at least one pin didn't reach home" in {
        val b5 = b4.replaceCell(6, Cell("")).replaceCell(2, Cell("G1"))
        strategy.hasWon(player, b5) should be (false)
      }
    }
  }
}
