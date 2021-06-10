package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.controller.NewMessage
import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import java.awt.Font
import scala.swing.Swing.EmptyBorder
import scala.swing._

case class MessageBox (controller: ControllerInterface) extends TextArea {
  listenTo(controller)

  border = EmptyBorder(20)
  font = new Font(Font.MONOSPACED, Font.BOLD, 16)

  reactions += {
    case NewMessage() => text = controller.messageToString()
    case _ =>
    repaint
  }
}
