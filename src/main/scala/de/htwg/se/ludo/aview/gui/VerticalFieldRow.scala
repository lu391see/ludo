package de.htwg.se.ludo.aview.gui
import scala.swing._

case class VerticalFieldRow(
    beginPos: Int,
    endPos: Int
) extends BoxPanel(orientation = Orientation.Vertical){
  for (pos <- beginPos to endPos) {
    contents += new Field(pos)
  }
}
