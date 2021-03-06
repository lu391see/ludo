package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}

import scala.swing._

class VerticalFieldRow(
    override val startFieldColor: Color,
    override val startFieldPos: Int,
    endPos: Int,
    reverse: Boolean,
    override val controller: ControllerInterface
) extends FieldRow(
      orientation = Orientation.Vertical,
      controller = controller,
      startFieldPos = startFieldPos,
      startFieldColor = startFieldColor
    ) {

  if (!reverse) {
    contents += startField
  }
  private val range =
    if (reverse) (startFieldPos + 1 until endPos).reverse
    else startFieldPos + 1 until endPos
  for (pos <- range) {
    contents += new Field(pos)
  }
  if (reverse) {
    contents += startField
  }

}
