package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import java.awt.{Color, Font}
import scala.swing._
import scala.swing.event.MouseClicked

case class Pin(
    pinNumber: Int,
    color: Color,
    pos: Int,
    controller: ControllerInterface
) extends Field(pos) {
  background = color
  add(
    new Label {
      text = pinNumber.toString
      font = new Font(Font.MONOSPACED, Font.BOLD, 20)
      foreground = if (color == Color.black) Color.white else Color.black
    },
    BorderPanel.Position.Center
  )

  reactions += {
    case MouseClicked(src, pt, mod, clicks, pops) => {
      //controller.isDrawing
    }
  }
}
