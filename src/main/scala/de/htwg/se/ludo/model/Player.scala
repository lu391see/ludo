package de.htwg.se.ludo.model

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

  override def toString: String = {
    s"Player '${name}'"
  }
}
