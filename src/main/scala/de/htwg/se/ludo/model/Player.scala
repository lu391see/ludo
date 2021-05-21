package de.htwg.se.ludo.model

case class Player(name: String, team: Team) {
  var successfulSixRoll = false
  // TODO
  var hasFirstCycle = false

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

  override def toString: String = {
    s"Player ${team.color}: '${name}'"
  }
}
