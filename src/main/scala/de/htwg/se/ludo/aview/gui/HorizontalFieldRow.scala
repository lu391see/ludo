package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}

import scala.swing._

case class HorizontalFieldRow(
    beginColor: Color,
    endColor: Color,
    beginPos: Int,
    endPos: Int,
    reverse: Boolean,
    controller: ControllerInterface
) extends BoxPanel(orientation = Orientation.Horizontal) {
  listenTo(controller)
  val startField = new StartField(color = beginColor, pos = beginPos)
  val otherStartField = new StartField(color = endColor, pos = endPos)

  contents += startField
  val range = if(reverse) (beginPos until endPos).reverse else beginPos until endPos
  for (pos <- range) {
    contents += new Field(pos)
  }
  contents += otherStartField

  reactions += {
    case event: PinDrawn =>
      val oldPos = contents.indexWhere(c => c.name.toInt.equals(event.curPos))
      val newPos = contents.indexWhere(c => c.name.toInt.equals(event.nextPos))
      if (oldPos != -1) {
        var field = new Field(oldPos)
        if (event.curPos == beginPos) field = startField
        else if (event.curPos == endPos) field = otherStartField

        val newContents = contents.updated(oldPos, field)
        contents.clear()
        contents ++= newContents
      }
      if (newPos != -1) {
        val newContents = contents.updated(
          newPos,
          Pin(event.pinId, event.color, event.nextPos, controller)
        )
        contents.clear()
        contents ++= newContents
      }
      repaint
  }
}
