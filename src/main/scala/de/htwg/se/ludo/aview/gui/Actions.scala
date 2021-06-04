package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.Controller

import java.awt.{Color, Font}
import javax.swing.border.LineBorder
import scala.swing.{Button, Component, Dimension, FlowPanel}

case class Actions (controller: Controller) extends FlowPanel {
  // TODO: Test
  val rollButton: Button = Button("Roll") {
    controller.handleInput("")
  }
  val undoButton: Button = Button("Undo") {
    controller.undo()
  }
  val redoButton: Button = Button("Redo") {
    controller.redo()
  }

  rollButton.background = Color.red.brighter().darker()
  rollButton.border = new LineBorder(Color.white, 1)
  rollButton.font =  new Font(Font.MONOSPACED, Font.BOLD, 20)

  undoButton.background = Color.green.brighter().darker()
  undoButton.border = new LineBorder(Color.white, 1)
  undoButton.font =  new Font(Font.MONOSPACED, Font.BOLD, 20)

  redoButton.background = Color.yellow.brighter().darker()
  redoButton.border = new LineBorder(Color.white, 1)
  redoButton.font =  new Font(Font.MONOSPACED, Font.BOLD, 20)

  rollButton.preferredSize_=(new Dimension(120, 30))
  undoButton.preferredSize_=(new Dimension(120, 30))
  redoButton.preferredSize_=(new Dimension(120, 30))
  listenTo(rollButton, undoButton, redoButton)

  contents += rollButton
  contents += undoButton
  contents += redoButton
}
