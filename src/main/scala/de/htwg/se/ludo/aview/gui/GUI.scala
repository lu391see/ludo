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


case class GUI(controller: Controller) extends Frame {

    listenTo(controller)
    title = "Ludo"
}
