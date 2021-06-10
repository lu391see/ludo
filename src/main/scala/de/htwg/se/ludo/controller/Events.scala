package de.htwg.se.ludo.controller

import java.awt.Color
import scala.swing.event.Event

case class Undo() extends Event
case class NewGame() extends Event
case class PinDrawn(color: Color, pinId: Int, curPos: Int, nextPos: Int) extends Event {
  override def toString: String = {
    "color="+color.toString+",pinId="+pinId.toString+",curPos="+curPos.toString+",nextPos="+nextPos.toString
  }
}
case class NewMessage() extends Event
case class NewPlayer() extends Event
case class Redo() extends Event