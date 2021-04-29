package de.htwg.se.ludo.view

import de.htwg.se.ludo.model._
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
  "A TUI processing pins with a dice of 4" should {
    val players: Array[Player] = new Array[Player](1)
    players(0) = Player("", 1)
    var game = Game(new Field(40, Cell(0)), players.toVector)
    val tui = new Tui
    val first_player = players(0)
    val diceFour = new Dice(4)

    "should for the first drawn pin have the fourth cell set to the pin's index" in {
      val input = "1"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.field.spots(4) should be(Cell(first_player.playerPins(0).index))
    }
    "should for the second drawn pin have the fourth cell set to the pin's index" in {
      val input = "2"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.field.spots(4) should be(Cell(first_player.playerPins(1).index))
    }
    "should for the third drawn pin have the fourth cell set to the pin's index" in {
      val input = "3"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.field.spots(4) should be(Cell(first_player.playerPins(2).index))
    }
    "should for the fourth drawn pin have the fourth cell set to the pin's index" in {
      val input = "4"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.field.spots(4) should be(Cell(first_player.playerPins(3).index))
    }
  }
}
