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

    val players: Vector[Player] = Vector.tabulate(playerAmount) {
      n => Player(readLine(s"Player ${n + 1}, type your name: "), n + 1)
    }

    var game = Game(new Field(40, Cell(0)), players)
    val tui = new Tui

    var turnCounter = 0

    var input: String = ""

    while(true) {
      val dice = RandomDice()
      println(s"""
            |Current Game Status:
            |${game.field.toString}
            |==> ${players(turnCounter).name} can walk ${dice.throwDice()} please choose a pin (1-4)!""".stripMargin)

      input = readLine()
      if (input == "q") {
        return
      }
      game = tui.processInputLine(input, game, players(turnCounter), dice)

      turnCounter += 1
      turnCounter %= playerAmount
    }
  }
}