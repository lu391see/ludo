package de.htwg.se.ludo.model.playerComponent

import java.awt.Color

object PlayerConstraints {
  val teams = Vector(
    new Team(Color.black, 0, 16, 56),
    new Team(Color.red, 4, 26, 60),
    new Team(Color.green, 8, 36, 64),
    new Team(Color.yellow, 12, 46, 68)
  )
  val maxPlayers = 4
  val minPlayers = 2
  val totalPins: Int = maxPlayers * 4
}
