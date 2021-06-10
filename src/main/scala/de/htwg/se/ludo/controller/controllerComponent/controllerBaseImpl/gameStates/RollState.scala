package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.diceComponent.dice6Impl.Dice
import de.htwg.se.ludo.util.{ChoosePinMessage, State}

case class RollState(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    controller.rollDice(Dice())
    controller.newMessage(ChoosePinMessage)
    n.nextState(DrawState(controller))
  }
}
