package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{ChoosePinMessage, RollDiceMessage}
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
        controller.drawPin(pin - 1)

      case Failure(_) =>
        ChoosePinMessage.print()
        return

    }

    controller.switchPlayer()
    RollDiceMessage.print()
    if(controller.isWon) {
      println("Congratulations " + controller.currentPlayer + " has won the game!")
      System.exit(0)
    }

    controller.nextPlayer()
    println(controller.currentPlayer.get + " is next. Press any key to throw a Dice")
    n.nextState(RollState(controller))
  }

  def invalidPin(pin: Int): Boolean = {
    pin < 1 || pin > 4
  }

  def toInt(input: String): Try[Int] = {
    Try(input.toInt)
  }
}
