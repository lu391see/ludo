package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.Controller

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

  rollButton.preferredSize_=(new Dimension(120, 30))
  undoButton.preferredSize_=(new Dimension(120, 30))
  redoButton.preferredSize_=(new Dimension(120, 30))
  listenTo(rollButton, undoButton, redoButton)

  contents += rollButton
  contents += undoButton
  contents += redoButton
}
