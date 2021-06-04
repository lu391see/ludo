package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.{
  Controller,
  NewGame,
  NewMessage,
  PinDrawn,
  Redo,
  Undo
}

import scala.util.{Failure, Try}
import scala.swing.Reactor

class TUI(controller: Controller) extends Reactor {

  listenTo(controller)

  reactions += {
    case NewMessage()                             => onNewMessage
    case PinDrawn(_, _, _, _) | NewGame() | Undo() | Redo() => onBoardUpdate
  }

  def processInput(input: String): Unit = {
    input match {
      case "one" =>
        Try { controller.setWinStrategy("one") } match {
          case Failure(e) => println(e.getMessage)
        }
      case "all" =>
        Try { controller.setWinStrategy("all") } match {
          case Failure(e) => println(e.getMessage)
        }
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _   => controller.handleInput(input)
    }
  }

  def onBoardUpdate(): Unit = {
    println(controller)
  }

  def onNewMessage(): Unit = {
    controller.message.print()
  }
}
