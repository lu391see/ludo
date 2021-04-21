package de.htwg.se.ludo
import de.htwg.se.ludo.view.Tui
import model._

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {

    val playerAmount: Int = readLine("""
        |Welcome to Ludo aka 'Mensch Aergere Dich Nicht'!
        |How many players want to play?
        |Type between 1-4: """.stripMargin).toInt

    val players: Array[Player] = new Array[Player](playerAmount)
    var setup_str: String = "Hello"

    for (player_counter <- 1 until  playerAmount + 1) {
      val player_name : String = readLine(s"Player $player_counter, type your name: ")
      players(player_counter - 1) = Player(player_name, player_counter)
      setup_str += ", " + players(player_counter - 1).name
    }

    var game = Game(new Field(40, Cell(0)), players.toVector)
    //var game = new Field[Cell](40, Cell(0))
    val tui = new Tui
    println(setup_str)

    var turnCounter = 0

    var input: String = ""

    while(input != "q") {
      val dice = Dice()
      println(s"""
            |Current Game Status:
            |${game.field.toString}
            |==> ${players(turnCounter).name} can walk ${dice.t1} please choose a pin (1-4)!""".stripMargin)

      input = readLine()
      game = tui.processInputLine(input, game, players(turnCounter), dice)

      turnCounter += 1
      turnCounter %= playerAmount
    }
  }
}