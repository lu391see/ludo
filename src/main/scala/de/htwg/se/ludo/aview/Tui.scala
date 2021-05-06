package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.model.{Dice, Player}
import de.htwg.se.ludo.util.Observer

class Tui(controller: Controller) extends Observer {

  controller.add(this)

  def processInputLine(input: String, player: Player, dice: Dice): Unit = {
    input match {
      case "1"|"2"|"3"|"4" => controller.draw(player, input.toInt - 1, dice.throwDice())
      case _   =>
        val new_input = scala.io.StdIn.readLine("No valid Pin, try again!\n")
        processInputLine(new_input, player, dice)
    }
  }

  override def update(): Boolean = { println("Current Game Status:" + controller.gameToString); true}
}