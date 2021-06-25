package de.htwg.se.ludo.model.fileIoComponent

import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.playerComponent.Player

trait FileIOInterface {
  // todo: maybe use another structure
  def load(): (GameInterface, Int)
  def save(currentPlayer: Player, game: GameInterface): Unit
}
