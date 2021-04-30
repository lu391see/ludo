package de.htwg.se.ludo.model

case class Field(spots: Vector[Cell]) {
  def this(size: Int, filling: Cell) = this(Vector.tabulate(size) { _ => filling })

  val size: Int = spots.size

  def fill(filling:Cell):Field = copy(Vector.tabulate(size){_ => filling})

  def cell(spot: Int): Cell = spots(spot)

  def replaceCell(spot: Int, cell: Cell): Field = copy(spots.updated(spot, cell))

  override def toString: String = {
    var s = ""
    for {spot <- 0 until size} yield {s += cell(spot).toString}
    s
  }
}
