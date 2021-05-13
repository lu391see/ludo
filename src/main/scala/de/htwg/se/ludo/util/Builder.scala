package de.htwg.se.ludo.util

import de.htwg.se.ludo.model.Team

trait Builder {
  def setPlayerName(name: String): Builder
  def setPlayerTeam(team: Team): Builder
}
