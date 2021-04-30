package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Cell, Field, Game, Player}
import de.htwg.se.ludo.util.Observable

class Controller(var game: Game) extends Observable{
  def createStartGame(size: Int, players: Vector[Player]): Unit = {
    game = Game(new Field[Cell](size, Cell(0)), players)
    notifyObservers()
  }

  def gameToString: String = game.toString

  def draw(player: Player, pin: Int, dice_roll: Int): Unit = {
    game = game.draw_pin(player, pin, dice_roll)
    notifyObservers()
  }

}
