package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{FirstPlayerMessage, InvalidCurrentPlayerAtSetupMessage, PlayerConstraints, RollDiceMessage}
import de.htwg.se.ludo.util.State

case class SetupState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    if (shouldStartTheGame(input)) {
      controller.currentPlayer match {
        case Some(_) => InvalidCurrentPlayerAtSetupMessage.print()
        case None => controller.currentPlayer = Some(controller.players(0))
      }
      controller.newGame()
      FirstPlayerMessage(controller.currentPlayer.get).print()
      RollDiceMessage.print()
      n.nextState(RollState(controller))
    } else {
      controller.addNewPlayer(input)
    }
  }

  def shouldStartTheGame(input: String): Boolean = {
    controller.players.size == PlayerConstraints.maxPlayers || input.contains("start")
  }
}
