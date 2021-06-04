package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.PinDrawn

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

  reactions += {
    case event: PinDrawn => {
      val oldPos = contents.indexWhere(c => c.name.toInt.equals(event.curPos))
      val newPos = contents.indexWhere(c => c.name.toInt.equals(event.nextPos))
      if(oldPos != -1) {
        var field = new Field(oldPos)
        if(oldPos == beginPos) field = new StartField(color = beginColor, pos = beginPos)
        else if(oldPos == endPos) field = new StartField(color = endColor, pos = endPos)

        val newContents = contents.updated(oldPos, field)
        contents.clear()
        contents ++= newContents
      }
      if(newPos != -1) {
        val newContents = contents.updated(newPos, Pin(event.pinId, event.color, newPos))
        contents.clear()
        contents ++= newContents
      }
    }
  }
}
