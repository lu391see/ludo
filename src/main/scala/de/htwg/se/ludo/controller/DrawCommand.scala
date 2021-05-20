package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Game, Player}
import de.htwg.se.ludo.util.Command

class DrawCommand(pin: Int, controller: Controller) extends Command {
  var memento: (Game, Player, GameState, Int) = (
    controller.game,
    controller.currentPlayer,
    controller.gameState,
    controller.pips
  )

  override def doStep: Unit = {
    memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips)
    controller.game = controller.game.draw(controller.currentPlayer, pin, controller.pips)
    controller.notifyObservers()
  }

  override def undoStep: Unit = {
    val new_memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips)
    controller.game = memento._1
    controller.currentPlayer = memento._2
    controller.gameState = memento._3
    controller.pips = memento._4
    memento = new_memento
    controller.notifyObservers()
  }

  override def redoStep: Unit = undoStep
}
