package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.{Controller, PinDrawn}

import scala.swing._

case class BaseTeam(color: Color, basePos: Int, controller: Controller)
    extends GridPanel(2, 2) {
  listenTo(controller)
  for (i <- 1 to 4) {
    contents += Pin(i, color, basePos + i - 1)
  }
  reactions += {
    case event: PinDrawn => {
      if (event.curPos != event.nextPos) {
        val oldPos = contents.indexWhere(c => c.name.toInt.equals(event.curPos))
        val newPos =
          contents.indexWhere(c => c.name.toInt.equals(event.nextPos))

        println("BaseTeam", oldPos, newPos)
        if (oldPos != -1) {
          val newContents = contents.updated(oldPos, new StartField(event.color, event.curPos))
          contents.clear()
          contents ++= newContents
          for (content <- contents) {
            println("BaseTeam: "+color.toString,content.name, content.background)
          }

        }
        if (newPos != -1) {
          val newContents =
            contents.updated(newPos, Pin(event.pinId, event.color, event.nextPos))
          contents.clear()
          contents ++= newContents
        }
        //repaint
      }
      repaint
    }
  }
}
