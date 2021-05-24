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
      }
      case "all" => Try {controller.setWinStrategy("all") } match {
        case Failure(e) => println(e.getMessage)
      }
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _   => controller.handleInput(input)
    }
  }

  override def update(): Boolean = {
    println("Current Game Status:\n" + controller.toString)
    true
  }
}