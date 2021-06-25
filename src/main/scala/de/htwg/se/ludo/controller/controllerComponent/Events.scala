package de.htwg.se.ludo.controller.controllerComponent

import de.htwg.se.ludo.model.gameComponent.BoardInterface

import java.awt.Color
import scala.swing.event.Event

case class Undo() extends Event
case class NewGame() extends Event
case class PinDrawn(oldBoard: BoardInterface, newBoard: BoardInterface) extends Event
case class NewMessage() extends Event
case class NewPlayer() extends Event
case class Redo() extends Event