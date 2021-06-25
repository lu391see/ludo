package de.htwg.se.ludo.model.fileIoComponent

import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.playerComponent.Player

trait FileIOInterface {
  // todo: maybe use another structure
  def load(): GameInterface
  def save(currentPlayer: Player, players: Vector[Player], game: GameInterface): Unit
}
