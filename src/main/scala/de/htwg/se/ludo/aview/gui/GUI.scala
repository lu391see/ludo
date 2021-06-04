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
  val board: Board = Board(controller)

  preferredSize = new Dimension(600, 800)

  listenTo(controller)
  title = "Ludo"
  visible = true
  redraw

  contents = new BorderPanel {
    add(actions, BorderPanel.Position.South)
    add(board, BorderPanel.Position.Center)
  }

  def redraw = {
    repaint
  }
}
