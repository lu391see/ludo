package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

import scala.io.StdIn.readLine

case class SetupState(controller: Controller) extends State[GameState] {
  override def handle(input: String, n: GameState): Unit = {
    if (controller.players.size == controller.maxPlayers || input.contains("start")) {
      controller.currentPlayer match {
        case Some(_) =>
        case None => controller.currentPlayer = Some(controller.players(0))
      }
      controller.newGame()
      n.nextState(RollState(controller))
      println(
        controller.currentPlayer.get + " begins\nThrow dice with any input"
      )
    } else {
      if(input.isEmpty) {
        println("Please enter a player name!")
        return
      }
      controller.addPlayer(input, controller.teams(controller.players.size))
    }
  }

}
