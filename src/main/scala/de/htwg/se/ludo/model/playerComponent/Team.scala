package de.htwg.se.ludo.model.playerComponent

import java.awt.Color

class Team(val color: Color,
    val basePosition: Int,
    val startPosition: Int,
    val homePosition: Int
) {
  var pins: Vector[Pin] = Vector.tabulate(4) { i =>
    Pin(toColorString + (i + 1).toString)
  }

  def toColorString: Char = {
    this.color match {
      case Color.black  => 'B'
      case Color.red    => 'R'
      case Color.yellow => 'Y'
      case Color.green  => 'G'
      case _            => '-'
    }
  }
}

case class EmptyTeam() extends Team(Color.yellow, 0, 0, 0)
