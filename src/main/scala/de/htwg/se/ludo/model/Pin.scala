package de.htwg.se.ludo.model

case class Pin (id: String, position: Int){
  def move(pos: Int): Pin = {
    Pin(id, pos)
  }

  def finish(homePosition: Int): Pin = {
    move(homePosition + index())
  }

  def base(basePosition: Int): Pin = {
    move(basePosition + index())
  }

  def index(): Int = {
    id.substring(1).toInt - 1
  }
}
