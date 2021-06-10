package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.PinDrawn
import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import scala.swing._

case class VerticalFieldRow(
    beginPos: Int,
    endPos: Int,
    controller: ControllerInterface
) extends BoxPanel(orientation = Orientation.Vertical){
  listenTo(controller)
  for (pos <- beginPos to endPos) {
    contents += new Field(pos)
  }

  reactions += {
    case event: PinDrawn => {
      val oldPos = contents.indexWhere(c => c.name.toInt.equals(event.curPos))
      val newPos = contents.indexWhere(c => c.name.toInt.equals(event.nextPos))
      if(oldPos != -1) {
        val newContents = contents.updated(oldPos, new Field(event.curPos))
        contents.clear()
        contents ++= newContents
      }
      if(newPos != -1) {
        val newContents = contents.updated(newPos, Pin(event.pinId, event.color, event.nextPos, controller))
        contents.clear()
        contents ++= newContents

      }
      repaint
    }
  }
}