package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.Controller

import scala.swing.Color

class HomeTeam(
    override val color: Color,
    override val basePos: Int,
    override val controller: Controller
) extends BaseTeam(color, basePos, controller) {
  contents.clear()
  for (i <- 1 to 4) {
    contents += new Field(basePos + i - 1)
  }
}
