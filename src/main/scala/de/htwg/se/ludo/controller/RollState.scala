package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.ChoosePinMessage
import de.htwg.se.ludo.util.State

case class RollState(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    controller.rollDice()
    controller.newMessage(ChoosePinMessage)
    n.nextState(DrawState(controller))
  }
}
