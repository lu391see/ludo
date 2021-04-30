package de.htwg.se.ludo
import de.htwg.se.ludo.view.Tui
import model._

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {

    val teams = Vector(
      Team('Y', 0, 15, 55),
      Team('G', 4, 25, 59),
      Team('R', 8, 35, 63),
      Team('B', 12, 45, 67)
    )

    val playerAmount: Int = readLine(
      """
        |Welcome to Ludo aka 'Mensch Aergere Dich Nicht'!
        |How many players want to play?
        |Type between 1-4: """.stripMargin
    ).toInt

    val players: Vector[Player] = Vector.tabulate(playerAmount) { n =>
      Player(readLine(s"Player ${n + 1}, type your name: "), teams(n))
    }

    val board = new Field(71, EmptyCell())
    var game = Game(board, players).base()
    val tui = new Tui

    var turnCounter = 0
    var input: String = ""

    while (true) {
      val player = players(turnCounter)
      if(player.hasWon()) {
        println(s"${player.name} has won!")
        return
      }
      val dice = RandomDice()
      println(s"""
            |Current Game Status:
            |${game.board.toString}
            |==> ${player.name} can walk ${dice
        .throwDice()} please choose a pin (1-4)!""".stripMargin)

      input = readLine()
      if (input == "q") {
        return
      }
      if(!input.matches("[1-4]")) {
        input = readLine("No valid Pin, try again!\n")
      }
      game = tui.processInputLine(input, game, player, dice)

      turnCounter += 1
      turnCounter %= playerAmount
    }
  }
}
