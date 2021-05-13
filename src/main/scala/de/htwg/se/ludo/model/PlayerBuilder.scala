package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.Builder

case class PlayerBuilder() extends Builder {
  var name: String = ""
  var team: Team = EmptyTeam()

  override def setPlayerName(name: String): PlayerBuilder = {
    this.name = name
    this
  }

  override def setPlayerTeam(team: Team): PlayerBuilder = {
    this.team = team
    this
  }

  def build(): Player = Player(name, team)
}
