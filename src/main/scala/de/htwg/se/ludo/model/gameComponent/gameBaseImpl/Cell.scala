package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import de.htwg.se.ludo.model.gameComponent.CellInterface

import java.awt.Color

sealed case class Cell(value: String) extends CellInterface {
  def isSet: Boolean = value != ""
  def color: Char = value.charAt(0)
  def pinNumber: Int = value.substring(1).toInt

  override def toString: String = {
    "[" + value + "]"
  }

  def colorFromChar: Color = {
    value.charAt(0) match {
      case 'B' => Color.black
      case 'R' => Color.red
      case 'Y' => Color.yellow
      case 'G' => Color.green
      case _   => Color.white
    }
  }
}
