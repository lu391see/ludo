package de.htwg.se.ludo.model

import de.htwg.se.ludo.view.Tui
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class TuiSpec extends AnyWordSpec with Matchers {
  "A TUI processing pins with a dice of 4" should {
    val players: Array[Player] = new Array[Player](1)
    players(0) = Player("", Team('Y', 0, 15, 55))
    var game = Game(new Board(71, EmptyCell(), 4), players.toVector)
    val tui = new Tui
    val first_player = players(0)
    val diceFour = new Dice(4)

    "should for the first drawn pin have the fourth cell set to the pin's index" in {
      val input = "1"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.board.spots(4) should be(new Cell(first_player.team.id(0)))
    }
    "should for the second drawn pin have the fourth cell set to the pin's index" in {
      val input = "2"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.board.spots(4) should be(new Cell(first_player.team.id(1)))
    }
    "should for the third drawn pin have the fourth cell set to the pin's index" in {
      val input = "3"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.board.spots(4) should be(new Cell(first_player.team.id(2)))
    }
    "should for the fourth drawn pin have the fourth cell set to the pin's index" in {
      val input = "4"
      game = tui.processInputLine(input, game, first_player, diceFour)
      game.board.spots(4) should be(new Cell(first_player.team.id(3)))
    }
  }
}
