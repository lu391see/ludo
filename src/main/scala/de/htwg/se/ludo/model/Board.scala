package de.htwg.se.ludo.model


case class Board(spots: Vector[Cell], baseSize: Int) {
  def this(size: Int, filling: Cell, baseSize: Int) = this(Vector.tabulate(size) { _ => filling }, baseSize)

  val size: Int = spots.size

  def fill(filling:Cell):Board = copy(Vector.tabulate(size){ _ => filling})

  def cell(spot: Int): Cell = spots(spot)

  def replaceCell(spot: Int, cell: Cell): Board = copy(spots.updated(spot, cell))

  def gameSize: Int = size - baseSize

  override def toString: String = {
    var s = "Base: "
    for {spot <- 0 until baseSize} yield {s += cell(spot).toString}
    s += "\nGame: "
    for {spot <- baseSize until gameSize} yield {s += cell(spot).toString}
    s += "\nHome: "
    for {spot <- gameSize until size} yield {s += cell(spot).toString}
    s
  }
}
