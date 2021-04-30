package de.htwg.se.ludo.view

import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.model.{Dice, Game, Player}
import de.htwg.se.ludo.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String, board: Game, player: Player, dice: Dice): Game = {
    input match {
      case "1"|"2"|"3"|"4" => board.draw_pin(player, input.toInt - 1, dice.throwDice())
      case _   =>
        val new_input = scala.io.StdIn.readLine("No valid Pin, try again!\n")
        processInputLine(new_input, board, player, dice)
    }
  }

  override def update(): Unit = println(controller.gameToString)
}