package de.htwg.se.ludo.model

case class Team(
    color: Char,
    basePosition: Int,
    startPosition: Int,
    homePosition: Int
) {

  var pins: Vector[Pin] = Vector.tabulate(4) { i =>
    Pin(color + (i + 1).toString, basePosition + i)
  }

  def spawnPin(pin: Int) {
    pins = pins.updated(pin, pins(pin).move(startPosition))
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
    pins(pin).position >= homePosition
  }

  def position(pin: Int): Int = {
    pins(pin).position
  }

  def id(pin: Int): String = {
    pins(pin).id
  }
}
