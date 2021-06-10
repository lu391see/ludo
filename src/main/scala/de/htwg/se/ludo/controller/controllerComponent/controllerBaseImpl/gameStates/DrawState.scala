package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.util.{ChoosePinMessage, RollDiceMessage, State}
import scala.util.{Failure, Success, Try}

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    toInt(input) match {
      case Success(pin) =>
        if (invalidPin(pin)) {
          controller.newMessage(ChoosePinMessage)
          return
        }
        controller.drawPin(pin - 1)

      case Failure(_) =>
        controller.newMessage(ChoosePinMessage)
        return

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