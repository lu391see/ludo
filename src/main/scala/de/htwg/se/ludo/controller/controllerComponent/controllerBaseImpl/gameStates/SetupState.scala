package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates

import com.google.inject.name.Names
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.util.{
  AddAnotherPlayerMessage,
  EnterPlayerNameMessage,
  FirstPlayerMessage,
  InvalidCurrentPlayerAtSetupMessage,
  RollDiceMessage,
  State
}
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

case class SetupState(controller: Controller) extends State[GameState] {
  override def handle(input: String, gameState: GameState): Unit = {

    if (input.isEmpty) {
      controller.newMessage(EnterPlayerNameMessage)
      return
    }

    if (shouldStartTheGame(input)) {
      if (controller.players.size <= 1) {
        controller.newMessage(AddAnotherPlayerMessage)
        return
      }
//      controller.currentPlayer match {
//        case Some(_) =>
//          controller.newMessage(InvalidCurrentPlayerAtSetupMessage)
//        case None => controller.currentPlayer = Some(controller.players(0))
//      }
      controller.currentPlayer = Some(controller.players(0))
      controller.newGame()
      controller.newMessage(FirstPlayerMessage(controller.currentPlayer.get))
      controller.newMessage(RollDiceMessage)
      gameState.nextState(RollState(controller))
    } else {
      controller.addNewPlayer(input)
    }
  }
  private def shouldStartTheGame(input: String): Boolean = {
    val max_players = controller.injector.instance[Int](Names.named("MaxPlayers"))
    controller.players.size == max_players || input.contains("start")
  }
}
