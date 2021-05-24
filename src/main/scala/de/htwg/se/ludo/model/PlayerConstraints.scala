package de.htwg.se.ludo.model

object PlayerConstraints {
  val teams = Vector(
    new Team('Y', 0, 16, 56),
    new Team('G', 4, 26, 60),
    new Team('R', 8, 36, 64),
    new Team('B', 12, 46, 68)
  )
  val maxPlayers = 4
  val minPlayers = 2
  val totalPins: Int = maxPlayers * 4
}
