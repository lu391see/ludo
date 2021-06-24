package de.htwg.se.ludo.model.fileIoComponent

import de.htwg.se.ludo.model.gameComponent.GameInterface

trait FileIOInterface {
  // todo: maybe use another structure
  def load(): GameInterface
  def save(game: GameInterface): Unit
}
