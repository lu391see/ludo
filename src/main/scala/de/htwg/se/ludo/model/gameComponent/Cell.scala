package de.htwg.se.ludo.model.gameComponent

sealed case class Cell(value: String) extends CellInterface {
  def isSet:Boolean = value != ""

  def getValue:String = value

  override def toString: String = {
    "[" + value + "]"
  }
}

object EmptyCell extends Cell("")