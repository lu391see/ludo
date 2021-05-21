package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Game, Pin, Player}
import de.htwg.se.ludo.util.Command

class DrawCommand(pin: Int, controller: Controller) extends Command {

  var memento: (Game, Player, GameState, Int, Vector[Pin]) = (
    controller.game,
    controller.currentPlayer,
    controller.gameState,
    controller.pips,
    getPins
  )

  override def doStep: Unit = {
    memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips)
    controller.game = controller.game.draw(controller.currentPlayer, pin, controller.pips)
    controller.notifyObservers()
  }

  override def undoStep: Unit = {
    val new_memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips, getPins)

    controller.game = memento._1
    controller.currentPlayer = memento._2
    controller.gameState = memento._3
    controller.pips = memento._4
    controller.players.foreach { player =>
      if (player == controller.currentPlayer) {
        player.team.pins = memento._5
      }
    }
    memento = new_memento
    controller.notifyObservers()
  }

  override def redoStep: Unit = undoStep

  private def getPins: Vector[Pin] = {
    var pins: Vector[Pin] = Vector.empty
    controller.players.foreach { player =>
      if (player == controller.currentPlayer) {
        pins = player.team.pins
      }
    }
    pins
  }
}
