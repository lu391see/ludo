package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

import scala.io.StdIn.readLine

case class SetupState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    if (controller.players.size == controller.maxPlayers || input.contains("start")) {
      controller.newGame()
      controller.currentPlayer = controller.players(0)
      n.nextState(RollState(controller))
      println(
        controller.currentPlayer + " begins\nThrow dice with any input"
      )
    } else {
      controller.addPlayer(input, controller.teams(controller.players.size))
    }
  }

}
