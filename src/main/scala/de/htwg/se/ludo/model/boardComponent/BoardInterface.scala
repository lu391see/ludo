package de.htwg.se.ludo.model.boardComponent

import de.htwg.se.ludo.model.playerComponent.Player

trait GameInterface {
  def based(): GameInterface
  def draw(currentPlayer: Player, pin: Int, dice_roll: Int): GameInterface
}

trait BoardInterface {
  val size: Int
  val gameSize: Int
  def cell(spot: Int): CellInterface
  def replaceCell(spot: Int, cell: CellInterface): BoardInterface
}

trait CellInterface {
  def isSet:Boolean
  def getValue:String
}