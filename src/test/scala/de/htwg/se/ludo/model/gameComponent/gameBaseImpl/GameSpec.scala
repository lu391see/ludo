package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import de.htwg.se.ludo.model.playerComponent.{Player, Team}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

class GameSpec extends AnyWordSpec with Matchers {
  "A game" when {
    /*"created with one player and the first pin drawn with dice roll 4" should {
      val players: Array[Player] = new Array[Player](1)
      players(0) = Player("p1", new Team(Color.yellow, 0, 15, 55))
      val empty_cell = Cell("")
      val game = Game(new Board(71, empty_cell, 4), players.toVector)
      val first_player = players(0)
      first_player.sixRolled = true
      val first_pin = 0
      val dice_roll = 4
      val game_changed = game.draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, dice_roll)
      val default_pin_position = 0

      /* TODO
      val new_cell = Cell(first_player.team.pinID(first_pin))
      "have the cell with the default pin position to an empty cell" in {
        game_changed.board.spots(default_pin_position) should be(empty_cell)
      }
      "have the new pin position set to 4" in {
        first_player.team.position(first_pin) should be(dice_roll + first_player.team.startPosition)
      }
      "have the cell with the new pin position set to the pin index" in {
        game_changed.board.spots(dice_roll+ first_player.team.startPosition) should be(
          new_cell
        )
      }
       */
    }
  }
  "A game" when {
    "created with one player and the first pin drawn continuously with dice roll 6 until the end is reached" should {
      val players: Array[Player] = new Array[Player](1)
      players(0) = Player("", new Team(Color.yellow, 0, 16, 56))
      val empty_cell = Cell("")
      val game = Game(new Board(72, empty_cell, 4 * 4), players.toVector)
      val first_player = players(0)

      val first_pin = 0
      val game_changed = game
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
        .draw(first_player, first_pin, 6)
      "have the player won" in {
        //TODO first_player.hasWon should be(true)
      }
      "the cell with the last pin position set to an empty cell" in {
        game_changed.board.spots(6 * 6) should be(empty_cell)
      }
    }*/
  }
}
