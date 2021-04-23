package de.htwg.se.ludo.model

import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class GameSpec extends AnyWordSpec with Matchers {
  "A game" when {
    "created with one player and the first pin drawn with dice roll 4" should {
      val players: Array[Player] = new Array[Player](1)
      players(0) = Player("", 1)
      val game = Game(new Field(40, Cell(0)), players.toVector)
      val first_player = players(0)
      val first_pin = 0
      val dice_roll = 4
      val game_changed = game.draw_pin(first_player, first_pin, dice_roll)
      val default_pin_position = 0
      val empty_cell = Cell(0)
      val new_cell = Cell(first_player.playerPins(first_pin).index)
      "have the cell with the default pin position to an empty cell" in {
        game_changed.field.spots(default_pin_position) should be(empty_cell)
      }
      "have the new pin position set to 4" in {
        first_player.playerPins(first_pin).position should be(dice_roll)
      }
      "have the cell with the new pin position set to the pin index" in {
        game_changed.field.spots(dice_roll) should be(
          new_cell
        )
      }
    }
  }
  "A game" when {
    "created with one player and the first pin drawn continuously with dice roll 6 until the end is reached" should {
      val players: Array[Player] = new Array[Player](1)
      players(0) = Player("", 1)
      val game = Game(new Field(40, Cell(0)), players.toVector)
      val first_player = players(0)
      val empty_cell = Cell(0)
      val first_pin = 0
      val game_changed = game
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
        .draw_pin(first_player, first_pin, 6)
      "have the player won" in {
        first_player.hasWon should be(true)
      }
      "the cell with the last pin position set to an empty cell" in {
        game_changed.field.spots(6 * 6) should be(empty_cell)
      }
    }
  }
}
