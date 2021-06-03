package de.htwg.se.ludo.aview.gui
import scala.swing._

case class HorizontalFieldRow(
    beginColor: Color,
    endColor: Color,
    beginPos: Int,
    endPos: Int
) extends BoxPanel(orientation = Orientation.Horizontal) {
  contents += new StartField(color = beginColor, pos = beginPos)
  for (pos <- beginPos + 1 until endPos - 1) {
    contents += new Field(pos)
  }
  contents += new StartField(color = endColor, pos = endPos)
}
