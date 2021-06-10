package de.htwg.se.ludo.model.boardComponent.boardBaseImpl

sealed case class Cell(value: String) {
  def isSet:Boolean = value != ""

  def getValue:String = value

  override def toString: String = {
    "[" + value + "]"
  }
}

object EmptyCell extends Cell("")