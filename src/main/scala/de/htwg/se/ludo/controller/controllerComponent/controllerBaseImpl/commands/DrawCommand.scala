package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.commands

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.GameState
import de.htwg.se.ludo.model.playerComponent.{Pin, Player}
import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.util.{Command, GameBoardUninitializedMessage}

class DrawCommand(pin: Int, controller: Controller) extends Command {

  var memento: (Option[GameInterface], Option[Player], GameState, Int, Vector[Pin]) = (
    controller.game,
    controller.currentPlayer,
    controller.gameState,
    controller.pips,
    getPins
  )

  override def doStep: Unit = {
    memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips, getPins)
    controller.game match {
      case Some(g) => controller.game = Some(g.draw(controller.currentPlayer.get, pin, controller.pips))
      case None => controller.newMessage(GameBoardUninitializedMessage)
    }
  }

  override def undoStep: Unit = {
    val new_memento = (controller.game, controller.currentPlayer, controller.gameState, controller.pips, getPins)

    controller.game = memento._1
    controller.currentPlayer = memento._2
    controller.gameState = memento._3
    controller.pips = memento._4
    controller.players.foreach { player =>
      if (player == controller.currentPlayer.get) {
        player.team.pins = memento._5
      }
    }
    memento = new_memento
  }

  override def redoStep: Unit = undoStep

  private def getPins: Vector[Pin] = {
    var pins: Vector[Pin] = Vector.empty
    controller.players.foreach { player =>
      if (player == controller.currentPlayer.get) {
        pins = player.team.pins
      }
    }
    pins
  }
}
