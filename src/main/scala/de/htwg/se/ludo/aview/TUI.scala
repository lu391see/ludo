package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.util.{Observer, UI}

class TUI(controller: Controller) extends UI with Observer {

  controller.add(this)

  override def processInput(input: String): Unit = {
    input match {
      case "q" => System.exit(0)
      case "one" => controller.setWinStrategy("one") // won when one pin arrived in home
      case "all" => controller.setWinStrategy("all") // won when all 4 pins arrived in home
      case _   => controller.execute(input)
    }
  }

  override def update(): Boolean = {
    println("Current Game Status:\n" + controller.gameToString)
    true
  }
}