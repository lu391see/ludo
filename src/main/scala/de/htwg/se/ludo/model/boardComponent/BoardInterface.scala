package de.htwg.se.ludo.model.boardComponent

trait BoardInterface {
  val size: Int
  val gameSize: Int
  val baseSize: Int

  def cell(spot: Int): CellInterface
  def replaceCell(spot: Int, cell: CellInterface): BoardInterface
}

trait CellInterface {
  def isSet:Boolean
  def getValue:String
}