package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.util.{Observer, UI}

import scala.util.{Failure, Try}

class TUI(controller: Controller) extends UI with Observer {

  controller.add(this)

  override def processInput(input: String): Unit = {
    input match {
      case "one" => Try {controller.setWinStrategy("one") } match {
        case Failure(e) => println(e.getMessage)
      }// won when one pin arrived in home
      case "all" => Try {controller.setWinStrategy("all") } match {
        case Failure(e) => println(e.getMessage)
      } // won when all 4 pins arrived in home
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _   => controller.execute(input)
    }
  }

  override def update(): Boolean = {
    println("Current Game Status:\n" + controller.gameToString)
    true
  }
}