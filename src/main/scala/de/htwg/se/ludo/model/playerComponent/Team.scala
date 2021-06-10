package de.htwg.se.ludo.model.playerComponent

import java.awt.Color

class Team(
    val color: Color,
    val basePosition: Int,
    val startPosition: Int,
    val homePosition: Int
) {
  var pins: Vector[Pin] = Vector.tabulate(4) { i =>
    Pin(toColorString(color, i + 1), basePosition + i)
  }

  def toColorString(color: Color, pin: Int): String = {
    (color match {
      case Color.black  => "B"
      case Color.red    => "R"
      case Color.yellow => "Y"
      case Color.green  => "G"
      case _            => ""
    }) + pin.toString
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

  def pinID(pin: Int): String = {
    pins(pin).id
  }
}

case class EmptyTeam() extends Team(Color.yellow, 0, 0, 0)
