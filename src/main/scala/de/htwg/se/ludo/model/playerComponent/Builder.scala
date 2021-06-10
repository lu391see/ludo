package de.htwg.se.ludo.model.playerComponent

trait Builder {
  def setPlayerName(name: String): Builder
  def setPlayerTeam(team: Team): Builder
}
