package de.htwg.se.ludo.aview.gui

import java.awt.{Color, Font}
import scala.swing._

case class Pin(
    id: Int,
    color: Color,
    pos: Int,

) extends Field(pos) {
  background = color
  add(new Label {
    text = id.toString
    font = new Font(Font.MONOSPACED, Font.BOLD, 20)
    foreground = if (color == Color.black) Color.white else Color.black
  }, BorderPanel.Position.Center)
}
