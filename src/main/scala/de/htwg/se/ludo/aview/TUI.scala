package de.htwg.se.ludo.aview

import com.google.inject.name.Names
import com.google.inject.{Guice, Injector}
import de.htwg.se.ludo.LudoModule
import de.htwg.se.ludo.controller.controllerComponent._
import de.htwg.se.ludo.util.WinStrategy
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

import scala.swing.Reactor

class TUI(controller: ControllerInterface) extends Reactor {

  listenTo(controller)

  reactions += {
    case NewMessage()                                       => onNewMessage()
    case PinDrawn(_, _) | NewGame() | Undo() | Redo() => onBoardUpdate()
  }

  val injector: Injector = Guice.createInjector(new LudoModule)

  def processInput(input: String): Unit = {
    input match {
      case "one" => controller.setWinStrategy(injector.instance[WinStrategy](Names.named("OnePin")))
      case "all" => controller.setWinStrategy(injector.instance[WinStrategy](Names.named("AllPin")))
      case "z" => controller.undo()
      case "y" => controller.redo()
      case _   => controller.handleInput(input)
      // case "" =>
    }
  }

  def onBoardUpdate(): Unit = {
    println(controller)
  }

  def onNewMessage(): Unit = {
    controller.publishMessage()
  }
}
