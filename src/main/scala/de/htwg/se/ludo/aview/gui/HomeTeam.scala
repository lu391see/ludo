package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import scala.swing.{Color, GridPanel}

case class HomeTeam(
    color: Color,
    homePos: Int,
    controller: ControllerInterface
) extends GridPanel(2, 2) {
  for (i <- 1 to 4) {
    contents += new Field(homePos + i - 1)
  }
  listenTo(controller)


}
