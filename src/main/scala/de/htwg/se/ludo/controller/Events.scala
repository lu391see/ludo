package de.htwg.se.ludo.controller

import scala.swing.event.Event

case class Undo() extends Event
case class NewGame() extends Event
case class PinDrawn() extends Event
case class NewMessage() extends Event
case class NewPlayer() extends Event
case class Redo() extends Event