package de.htwg.se.ludo.aview.gui

import java.awt.{Color, Font}

case class Pin(
    id: Int,
    color: Color,
    pos: Int
) extends StartField(color, pos) {
  font = new Font(Font.MONOSPACED, Font.BOLD, 20)
}
