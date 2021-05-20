package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.Game
import de.htwg.se.ludo.util.Command

class DrawCommand(pin: Int, controller: Controller) extends Command {
  var memento: Game = controller.game

  override def doStep: Unit = {
    memento = controller.game
    controller.game = controller.game.draw(controller.game.currentPlayer, pin, controller.pips)
    controller.notifyObservers()
  }

  override def undoStep: Unit = {
    val new_memento = controller.game
    controller.game = memento
    memento = new_memento
    controller.notifyObservers()
  }

  override def redoStep: Unit = {
    val new_memento = controller.game
    controller.game = memento
    memento = new_memento
    controller.notifyObservers()
  }
}
