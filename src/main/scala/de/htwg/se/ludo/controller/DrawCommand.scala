package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.Command

class DrawCommand(pin: Int, controller: Controller) extends Command {

  var saveGame: SaveGame = currentSaveGame

  override def doStep: Unit = {
    saveGame = currentSaveGame
    controller.game match {
      case Some(g) =>
        controller.game = Some(
          g.draw(controller.currentPlayer.get, pin, controller.pips)
        )
      case None =>
        println(
          "\nCouldn't draw because the game was not initialized!\n"
        )
    }
    controller.notifyObservers()
  }

  override def undoStep: Unit = {
    val newSaveGame = currentSaveGame

    controller.game = saveGame.game
    controller.players = saveGame.players
    controller.currentPlayer = saveGame.currentPlayer
    controller.gameState = saveGame.gameState
    controller.pips = saveGame.pips
    saveGame = newSaveGame

    controller.notifyObservers()
  }

  override def redoStep: Unit = undoStep

  def currentSaveGame: SaveGame = SaveGame(
    controller.game,
    controller.players,
    controller.currentPlayer,
    controller.gameState,
    controller.pips
  )
}
