package de.htwg.se.ludo.model.playerComponent

case class Pin (id: String){
  def number: Int = {
    id.substring(1).toInt
  }
}
