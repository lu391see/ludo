package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{
  EnterPlayerNameMessage,
  AddAnotherPlayerMessage,
  FirstPlayerMessage,
  InvalidCurrentPlayerAtSetupMessage,
  PlayerConstraints,
  RollDiceMessage
}
import de.htwg.se.ludo.util.State

case class SetupState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    if (shouldStartTheGame(input)) {
      if (controller.players.size == 1) {
        AddAnotherPlayerMessage.print()
        return
      }
      controller.currentPlayer match {
        case Some(_) => InvalidCurrentPlayerAtSetupMessage.print()
        case None    => controller.currentPlayer = Some(controller.players(0))
      }
      controller.newGame()
      FirstPlayerMessage(controller.currentPlayer.get).print()
      RollDiceMessage.print()
      n.nextState(RollState(controller))
    } else {
      if(input.isEmpty) {
        EnterPlayerNameMessage.print()
        return
      }
      controller.addNewPlayer(input)
    }
  }
  def shouldStartTheGame(input: String): Boolean = {
    controller.players.size == PlayerConstraints.maxPlayers || input.contains(
      "start"
    )
  }
}
