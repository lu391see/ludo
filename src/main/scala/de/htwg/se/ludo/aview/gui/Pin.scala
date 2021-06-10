package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.Controller

import java.awt.{Color, Font}
import scala.swing._
import scala.swing.event.MouseClicked

case class Pin(
    id: Int,
    color: Color,
    pos: Int,
    controller: Controller
) extends Field(pos) {
  background = color
  add(new Label {
    text = id.toString
    font = new Font(Font.MONOSPACED, Font.BOLD, 20)
    foreground = if (color == Color.black) Color.white else Color.black
  }, BorderPanel.Position.Center)

  reactions += {
    case MouseClicked(src, pt, mod, clicks, pops) => {
      // TODO: move/spawn pin
    }
  }
}
