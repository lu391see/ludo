package de.htwg.se.ludo.model.boardComponent.boardBaseImpl

import de.htwg.se.ludo.model.boardComponent.BoardInterface
import de.htwg.se.ludo.model.gameComponent.CellInterface

case class Board(spots: Vector[CellInterface], baseSize: Int) extends BoardInterface {
  def this(size: Int, filling: CellInterface, baseSize: Int) =
    this(Vector.tabulate(size) { _ => filling }, baseSize)

  val size: Int = spots.size
  val gameSize: Int = size - baseSize

  def cell(spot: Int): CellInterface = spots(spot)

  def replaceCell(spot: Int, cell: CellInterface): BoardInterface =
    copy(spots.updated(spot, cell))

  override def toString: String = {
    var s = "Base: "
    for { spot <- 0 until baseSize } yield { s += cell(spot).toString }
    s += "\nGame: "
    for { spot <- baseSize until gameSize } yield { s += cell(spot).toString }
    s += "\nHome: "
    for { spot <- gameSize until size } yield { s += cell(spot).toString }
    s
  }
}
