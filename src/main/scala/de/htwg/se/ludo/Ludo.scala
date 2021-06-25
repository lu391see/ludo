package de.htwg.se.ludo
import com.google.inject.Guice
import de.htwg.se.ludo.aview.TUI
import de.htwg.se.ludo.aview.gui.GUI
import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import de.htwg.se.ludo.util.WelcomeMessage

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {
    val injector = Guice.createInjector(new LudoModule)
    val controller = injector.getInstance(classOf[ControllerInterface])
    val tui = new TUI(controller)
    // GUI(controller)

    var input: String = ""

    controller.newMessage(WelcomeMessage)

    while (true) {
      input = readLine()

      if (input == "q") System.exit(0)

      tui.processInput(input)
    }
  }
}