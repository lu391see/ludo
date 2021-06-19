package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import scala.swing._

case class GUI(controller: ControllerInterface) extends MainFrame {
  val actions: Actions = Actions(controller)
  val messageBox: MessageBox = MessageBox(controller)
  val board: Board = Board(controller)


  preferredSize = new Dimension(600, 800)

  listenTo(controller)
  title = "Ludo"
  visible = true
  redraw

  contents = new BorderPanel {
    add(new BoxPanel(orientation = Orientation.Vertical) {
      contents += messageBox
      contents += actions
    }, BorderPanel.Position.South)
    add(board, BorderPanel.Position.Center)
  }

  def redraw = {
    repaint
  }
}
