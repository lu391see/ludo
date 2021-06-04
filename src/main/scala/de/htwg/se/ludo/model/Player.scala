package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.Builder

import java.awt.Color

case class Player(name: String, team: Team) {
  var sixRolled = false

  def spawn(pin: Int): Unit = {
    team.spawnPin(pin)
  }

  def move(pin: Int, pos: Int): Unit = {
    team.movePin(pin, pos)
  }

  def finish(pin: Int): Unit = {
    team.finishPin(pin)
  }

  def hasWon(): Boolean = {
    team.pins.forall(pin => team.isFinished(team.pins.indexOf(pin)))
  }

  def toColorString(color: Color): String = {
    color match {
      case Color.black  => "Black"
      case Color.red    => "Red"
      case Color.yellow => "Yellow"
      case Color.green  => "Green"
      case _            => ""
    }
  }

  override def toString: String = {
    s"Player ${toColorString(team.color)}: '${name}'"
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
