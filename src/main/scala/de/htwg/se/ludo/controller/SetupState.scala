package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{
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
        controller.newMessage(AddAnotherPlayerMessage)
        return
      }
      controller.currentPlayer match {
        case Some(_) => controller.newMessage(InvalidCurrentPlayerAtSetupMessage)
        case None    => controller.currentPlayer = Some(controller.players(0))
      }
      controller.newGame()
      controller.newMessage(FirstPlayerMessage(controller.currentPlayer.get))
      controller.newMessage(RollDiceMessage)
      n.nextState(RollState(controller))
    } else {
      controller.addNewPlayer(input)
    }
  }
  def shouldStartTheGame(input: String): Boolean = {
    controller.players.size == PlayerConstraints.maxPlayers || input.contains(
      "start"
    )
  }
}
