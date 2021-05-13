package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

case class RollState(controller: Controller) extends State[GameState] {
  override def handle(string: String, n: GameState): Unit = {
    controller.roll()
    n.nextState(DrawState(controller))
    println("please choose a pin [1-4]")
  }
}
