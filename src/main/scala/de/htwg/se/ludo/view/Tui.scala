package de.htwg.se.ludo.view

import de.htwg.se.ludo.model.{Dice, Player, Game}

class Tui {
  def processInputLine(input: String, board: Game, player: Player, dice: Dice): Game = {
    input match {
      case "q" => board
      case "1"|"2"|"3"|"4" => board.draw_pin(player, input.toInt - 1, dice.t1)
      case _   =>
        val new_input = scala.io.StdIn.readLine("No valid Pin, try again!\n")
        processInputLine(new_input, board, player, dice)
    }
  }
}