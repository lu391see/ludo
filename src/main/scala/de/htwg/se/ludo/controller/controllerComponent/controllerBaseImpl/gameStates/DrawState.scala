package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.util.{ChoosePinMessage, PlayerWonGameMessage, PinIsAlreadyFinishedMessage, RollDiceMessage}
import de.htwg.se.ludo.util.State

import scala.util.{Failure, Success, Try}

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    toInt(input) match {
      case Success(pin) =>
        if (invalidPin(pin)) {
          controller.newMessage(ChoosePinMessage)
          return
        }
        if (controller.currentPlayer.get.team.isFinished(pin - 1)) {
          controller.newMessage(PinIsAlreadyFinishedMessage(pin))
          return
        }
        controller.drawPin(pin - 1)

      case Failure(_) =>
        controller.newMessage(ChoosePinMessage)
        return

    }

    if (controller.isWon) {
      controller.newMessage(PlayerWonGameMessage(controller.currentPlayer.get))
      System.exit(0)
    }

    controller.switchPlayer()
    controller.newMessage(RollDiceMessage)
    n.nextState(RollState(controller))
  }

  def invalidPin(pin: Int): Boolean = {
    pin < 1 || pin > 4
  }

  def toInt(input: String): Try[Int] = {
    Try(input.toInt)
  }
}
