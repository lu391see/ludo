package de.htwg.se.ludo.model.boardComponent

import de.htwg.se.ludo.model.gameComponent.CellInterface

trait BoardInterface {
  val spots: Vector[CellInterface]
  val size: Int
  val gameSize: Int
  val baseSize: Int

  def cell(spot: Int): CellInterface
  def replaceCell(spot: Int, cell: CellInterface): BoardInterface
}