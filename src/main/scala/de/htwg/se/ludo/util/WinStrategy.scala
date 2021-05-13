package de.htwg.se.ludo.util

import de.htwg.se.ludo.model.Player

trait WinStrategy {
  def hasWon(player: Player): Boolean
}
