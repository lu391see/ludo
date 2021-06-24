package de.htwg.se.ludo.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.GameInterface

class FileIO extends FileIOInterface {
  override def load(): GameInterface = ???

  override def save(game: GameInterface): Unit = ???
}
