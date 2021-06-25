package de.htwg.se.ludo.model.gameComponent

import de.htwg.se.ludo.model.playerComponent.Player

trait GameInterface {
  val board: BoardInterface
  val players: Vector[Player]

  def based(): GameInterface
  def draw(currentPlayer: Player, pin: Int, dice_roll: Int): GameInterface
  def drawnPin(player: Player, pin: Int, pos: Int): GameInterface
  def findPinPosition(currentPlayer: Player, pinNumber: Int): Int
}

trait BoardInterface {
  val spots: Vector[CellInterface]
  val size: Int
  val gameSize: Int
  val baseSize: Int

  def cell(spot: Int): CellInterface
  def replaceCell(spot: Int, cell: CellInterface): BoardInterface
}

trait CellInterface {
  def isSet: Boolean
  def color: Char
  def pinNumber: Int
  val value: String
}
