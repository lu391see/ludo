package de.htwg.se.ludo.view

import de.htwg.se.ludo.model.{Dice, Player, Game}

class Tui {
  def processInputLine(input: String, board: Game, player: Player, dice: Dice): Game = {
    board.draw(player, input.toInt - 1, dice.throwDice())
  }
}