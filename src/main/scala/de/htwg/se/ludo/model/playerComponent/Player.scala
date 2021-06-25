package de.htwg.se.ludo.model.playerComponent

case class Player(name: String, team: Team) {
  var sixRolled: Boolean = false

  override def toString: String = {
    s"Player ${team.toColorString}: '$name'"
  }
}

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
