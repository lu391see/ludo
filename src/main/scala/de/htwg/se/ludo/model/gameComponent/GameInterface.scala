package de.htwg.se.ludo.model.gameComponent

import de.htwg.se.ludo.model.boardComponent.BoardInterface
import de.htwg.se.ludo.model.playerComponent.Player

trait GameInterface {
  val board: BoardInterface
  def draw(currentPlayer: Player, pin: Int, dice_roll: Int): GameInterface
  def drawnPin(player: Player, pin: Int, pos: Int): GameInterface
}
