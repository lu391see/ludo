package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.{
  Controller,
  NewGame,
  NewMessage,
  PinDrawn,
  Redo,
  Undo
}
import scala.swing._

case class GUI(controller: Controller) extends MainFrame {
  val actions: Actions = Actions(controller)

  listenTo(controller)
  title = "Ludo"
  visible = true
  redraw

  contents = new BorderPanel {
    add(actions, BorderPanel.Position.South)
  }

  def redraw = {
    repaint
  }
}
