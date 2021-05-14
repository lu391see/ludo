package de.htwg.se.ludo.model

case class Cell(value: String) {
  def isSet:Boolean = value != ""

  def getValue:String = value

  override def toString: String = {
    "[" + value + "]"
  }
}

//case class EmptyCell() extends Cell("")