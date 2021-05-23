package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

import scala.util.{Failure, Success, Try}

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    toInt(input) match {
      case Success(pin) => {
        if(pin < 1 || pin > 4) {
          println("please choose a valid pin")
          return
        }
        controller.draw(pin-1)
      }
      case Failure(_) => {
        println("please choose a valid pin")
        return
      }
    }

    if(controller.isWon) {
      println("Congratulations " + controller.currentPlayer + " has won the game!")
      System.exit(0)
    }

    controller.nextPlayer()
    println(controller.currentPlayer.get + " is next. Press any key to throw a Dice")
    n.nextState(RollState(controller))
  }

  def toInt(input: String): Try[Int] = {
    Try(input.toInt)
  }
}
