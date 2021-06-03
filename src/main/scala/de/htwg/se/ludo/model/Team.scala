package de.htwg.se.ludo.model

class Team(
    val color: Char,
    val basePosition: Int,
    val startPosition: Int,
    val homePosition: Int
) {
  var pins: Vector[Pin] = Vector.tabulate(4) { i =>
    Pin(color + (i + 1).toString, basePosition + i)
  }

  def spawnPin(pin: Int) {
    pins = pins.updated(pin, pins(pin).move(startPosition))
  }

  def basePin(pin: Int): Unit = {
    pins = pins.updated(pin, pins(pin).base(basePosition))
  }

  def movePin(pin: Int, pos: Int): Unit = {
    pins = pins.updated(pin, pins(pin).move(pos))
  }

  def finishPin(pin: Int): Unit = {
    pins = pins.updated(pin, pins(pin).finish(homePosition))
  }

  def isSpawned(pin: Int): Boolean = {
    pins(pin).position > basePosition + 3
  }

  def isFinished(pin: Int): Boolean = {
    // TODO
    pins(pin).position >= homePosition - basePosition - 1
  }

  def isAtEndOfBoard(pin: Int): Boolean = {
    pins(pin).position == homePosition - basePosition - 1
  }

  def findPinAtPosition(pos: Int): Int = {
    pins.indexWhere(p => p.position == pos)
  }

  def position(pin: Int): Int = {
    pins(pin).position
  }

  def id(pin: Int): String = {
    pins(pin).id
  }
}

case class EmptyTeam() extends Team('Y', 0, 0, 0)
