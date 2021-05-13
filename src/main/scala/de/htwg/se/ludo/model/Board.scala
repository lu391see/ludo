package de.htwg.se.ludo.model


case class Board(spots: Vector[Cell], totalPins: Int) {
  def this(size: Int, filling: Cell, totalPins: Int) = this(Vector.tabulate(size) { _ => filling }, totalPins)

  val size: Int = spots.size

  def fill(filling:Cell):Board = copy(Vector.tabulate(size){ _ => filling})

  def cell(spot: Int): Cell = spots(spot)

  def replaceCell(spot: Int, cell: Cell): Board = copy(spots.updated(spot, cell))

  override def toString: String = {
    var s = "Base: "
    for {spot <- 0 until totalPins} yield {s += cell(spot).toString}
    s += "\nGame: "
    for {spot <- totalPins until size - totalPins} yield {s += cell(spot).toString}
    s += "\nHome: "
    for {spot <- size - totalPins until size} yield {s += cell(spot).toString}
    s
  }
}
