package de.htwg.se.ludo.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.playerComponent.Player

class FileIO extends FileIOInterface {
  override def load(): (GameInterface, Int) = ???

  override def save(currentPlayer: Player, game: GameInterface): Unit = ???
}
