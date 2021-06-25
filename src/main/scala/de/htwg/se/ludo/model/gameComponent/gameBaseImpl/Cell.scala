package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import de.htwg.se.ludo.model.gameComponent.CellInterface

sealed case class Cell(value: String) extends CellInterface {
  def isSet: Boolean = value != ""
  def color: Char = value.charAt(0)
  def pinNumber: Int = value.substring(1).toInt

  override def toString: String = {
    "[" + value + "]"
  }
}
