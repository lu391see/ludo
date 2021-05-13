package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.util.{Observer, UI}

case class GUI(controller: Controller) extends UI with Observer {
  override def processInput(input: String): Unit = {}
  override def update(): Boolean = {
    true
  }
}
