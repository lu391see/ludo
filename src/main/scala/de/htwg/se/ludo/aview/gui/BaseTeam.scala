package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.PinDrawn

import scala.swing._

case class BaseTeam(color: Color, basePos: Int) extends GridPanel(2,2) {
  for (i <- 1 to 4) {
    contents += Pin(i, color, basePos + i - 1)
  }

  reactions += {
    case event: PinDrawn => {
      val oldPos = contents.indexWhere(c => c.name.toInt.equals(event.curPos))
      val newPos = contents.indexWhere(c => c.name.toInt.equals(event.nextPos))
      if(oldPos != -1) {
        val newContents = contents.updated(oldPos, new Field(oldPos))
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
