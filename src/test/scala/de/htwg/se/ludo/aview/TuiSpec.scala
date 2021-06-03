package de.htwg.se.ludo.aview

import de.htwg.se.ludo.model._
import de.htwg.se.ludo.controller._
import de.htwg.se.ludo.util.UI
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
  "A TUI processing pins with a dice of 4" should {

    val players: Vector[Player] = Vector[Player](Player("",  new Team('Y', 0, 15, 55)))
    val controller = new Controller() // Game(new Field(40, Cell(0)), players)
    val tui = new TUI(controller)

    val first_player = players(0)
    val diceFour = new Dice(4)
    /*

    "should for the first drawn pin have the fourth cell set to the pin's index" in {
      val input = "1"
      tui.processInput(input)
      controller.game.board.spots(4) should be(Cell(first_player.playerPins(0).index))
    }
    "should for the second drawn pin have the fourth cell set to the pin's index" in {
      val input = "2"
      tui.processInput(input, first_player, diceFour)
      controller.game.field.spots(4) should be(Cell(first_player.playerPins(1).index))
    }
    "should for the third drawn pin have the fourth cell set to the pin's index" in {
      val input = "3"
      tui.processInput(input, first_player, diceFour)
      controller.game.field.spots(4) should be(Cell(first_player.playerPins(2).index))
    }
    "should for the fourth drawn pin have the fourth cell set to the pin's index" in {
      val input = "4"
      tui.processInput(input, first_player, diceFour)
      controller.game.field.spots(4) should be(Cell(first_player.playerPins(3).index))
    }*/
  }
}
