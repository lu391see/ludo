package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.commands

import de.htwg.se.ludo.model.{Player, PlayerBuilder, Team}
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.playerComponent.{PlayerBuilder, Team}
import de.htwg.se.ludo.util.Command

class AddPlayerCommand(name: String, team: Team, controller: Controller) extends Command {
  var undonePlayer: Option[Player] = None
  override def doStep: Unit = controller.players = {
    val builder = PlayerBuilder()
    val player = builder.setPlayerName(name).setPlayerTeam(team).build()
    controller.players.appended(player)
  }

  override def undoStep: Unit = {
    undonePlayer = Some(controller.players.last)
    controller.players = controller.players.dropRight(1)
  }

  override def redoStep: Unit = {
    val _undonePlayer = undonePlayer
    undoStep
    controller.players = controller.players.appended(_undonePlayer.get)
  }
}
