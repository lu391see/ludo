package de.htwg.se.ludo.model.playerComponent

import java.awt.Color

case class Player(name: String, team: Team) {
  var sixRolled: Boolean = false

  def spawn(pin: Int): Unit = {
    team.spawnPin(pin)
  }

  def move(pin: Int, pos: Int): Unit = {
    team.movePin(pin, pos)
  }

  def finish(pin: Int): Unit = {
    team.finishPin(pin)
  }

  def hasWon: Boolean = {
    team.pins.forall(pin => team.isFinished(team.pins.indexOf(pin)))
  }

  private def toColorString: String = {
    this.team.color match {
      case Color.black  => "Black"
      case Color.red    => "Red"
      case Color.yellow => "Yellow"
      case Color.green  => "Green"
      case _            => ""
    }
  }

  override def toString: String = {
    s"Player $toColorString: '$name'"
  }
}

case class PlayerBuilder() extends Builder {
  var name: String = ""
  var team: Team = EmptyTeam()  // TODO: make optional instead

  override def setPlayerName(name: String): PlayerBuilder = {
    this.name = name
    this
  }

  override def setPlayerTeam(team: Team): PlayerBuilder = {
    this.team = team
    this
  }

  def build(): Player = Player(name, team)
  /*team match {
    case Some(team) => Player(name, team)*/
}
