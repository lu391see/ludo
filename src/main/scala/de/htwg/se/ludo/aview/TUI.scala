package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.controllerComponent.{ControllerInterface, NewGame, NewMessage, PinDrawn, Redo, Undo}
import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}

import scala.swing.Reactor

class TUI(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  reactions += {
    case NewMessage()                                       => onNewMessage()
    case PinDrawn(_, _, _, _) | NewGame() | Undo() | Redo() => onBoardUpdate()
  }

  def processInput(input: String): Unit = {
    input match {
      case "one" => controller.setWinStrategy(OnePinWinStrategy())
      case "all" => controller.setWinStrategy(AllPinWinStrategy())
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _   => controller.handleInput(input)
    }
  }

  def onBoardUpdate(): Unit = {
    println(controller)
  }

  def onNewMessage(): Unit = {
    controller.publishMessage()
  }
}
