package de.htwg.se.ludo.view

import de.htwg.se.ludo.model.{Dice, Player, Game}

class Tui {
  def processInputLine(input: String, game: Game, player: Player, dice: Dice): Game = {
    game.draw(player, input.toInt - 1, dice.throwDice())
  }
}