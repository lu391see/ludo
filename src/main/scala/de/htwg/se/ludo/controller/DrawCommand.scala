package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Game, Player}
import de.htwg.se.ludo.util.Command

class DrawCommand(pin: Int, controller: Controller) extends Command {
  var memento: (Game, Player) = (controller.game, controller.currentPlayer)

  override def doStep: Unit = {
    memento = (controller.game, controller.currentPlayer)
    controller.game = controller.game.draw(controller.currentPlayer, pin, controller.pips)
    controller.notifyObservers()
  }

  override def undoStep: Unit = {
    val new_memento = (controller.game, controller.currentPlayer)
    controller.game = memento._1
    controller.currentPlayer = memento._2
    memento = new_memento
    controller.notifyObservers()
  }

  override def redoStep: Unit = {
    val new_memento = (controller.game, controller.currentPlayer)
    controller.game = memento._1
    controller.currentPlayer = memento._2
    memento = new_memento
    controller.notifyObservers()
  }
}
