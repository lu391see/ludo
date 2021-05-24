package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{
  ChoosePinMessage,
  PinIsAlreadyBasedMessage,
  RollDiceMessage
}
import de.htwg.se.ludo.util.State

import scala.util.{Failure, Success, Try}

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    toInt(input) match {
      case Success(pin) =>
        if (invalidPin(pin)) {
          ChoosePinMessage.print()
          return
        }
        if (basedPinShouldNotBeDrawn(pin - 1)) {
          PinIsAlreadyBasedMessage(pin).print()
          return
        }
        controller.drawPin(pin - 1)

      case Failure(_) =>
        ChoosePinMessage.print()
        return

    }

    controller.switchPlayer()
    RollDiceMessage.print()
    n.nextState(RollState(controller))
  }

  def invalidPin(pin: Int): Boolean = {
    pin < 1 || pin > 4
  }

  def toInt(input: String): Try[Int] = {
    Try(input.toInt)
  }

  def basedPinShouldNotBeDrawn(pin: Int): Boolean = {
    val team = controller.currentPlayer.get.team
    if (everyPinIsBased()) return false
    !team.isSpawned(pin) && controller.pips != 6
  }

  def everyPinIsBased(): Boolean = {
    val team = controller.currentPlayer.get.team
    team.pins.forall(p => !team.isSpawned(team.pins.indexOf(p)))
  }
}
