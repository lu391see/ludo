package de.htwg.se.ludo
import de.htwg.se.ludo.aview.TUI
import de.htwg.se.ludo.aview.gui.GUI
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.WelcomeMessage

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {

    val controller = new Controller()
    val tui = new TUI(controller)
    GUI(controller)

    var input: String = ""

    controller.newMessage(WelcomeMessage)

    while (true) {
      input = readLine()
      if (input == "q") return
      tui.processInput(input)
    }
  }
}