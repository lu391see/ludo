package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.util.{ChoosePinMessage, RollDiceMessage, State}

case class RollState(controller: Controller) extends State[GameState] {
  override def handle(input: String, state: GameState): Unit = {
    controller.rollDice()
    if(controller.shouldNotDraw) {
      controller.switchPlayer()
      controller.newMessage(RollDiceMessage)
      return
    }
    controller.newMessage(ChoosePinMessage)
    state.nextState(DrawState(controller))
  }
}
