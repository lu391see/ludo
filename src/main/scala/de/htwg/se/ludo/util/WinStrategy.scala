package de.htwg.se.ludo.util

import de.htwg.se.ludo.model.gameComponent.BoardInterface
import de.htwg.se.ludo.model.playerComponent.Player

trait WinStrategy {
  def hasWon(player: Player, board: BoardInterface): Boolean
}
