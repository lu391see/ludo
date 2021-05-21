package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

import scala.util.Try

case class DrawState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    /*if (input == "n") {
      n.nextState(SetupState(controller))
      return
    }*/
    toInt(input) match {
      case Some(pin) => {
        if(pin < 1 || pin > 4) {
          println("please choose a valid pin")
          return
        }
        controller.draw(pin-1)
      }
      case None => {
        println("please choose a valid pin")
        return
      }
    }

    controller.nextPlayer()
    println(controller.currentPlayer.get + " is next. Press any key to throw a Dice")
    n.nextState(RollState(controller))
  }

  def toInt(input: String): Option[Int] = {
    Try(input.toInt).toOption
  }
}
