package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    /*if (input == "n") {
      n.nextState(SetupState(controller))
      return
    }*/
    val pin = input.toInt // FIXME: Option/Try implementation at this point?
    if(pin < 1 || pin > 4) {
      println("please choose a valid pin")
      return
    }

    controller.draw(pin-1)
    controller.game.nextPlayer()
    println(controller.game.currentPlayer + " is next. Press any key to throw a Dice")
    n.nextState(RollState(controller))
  }
}
