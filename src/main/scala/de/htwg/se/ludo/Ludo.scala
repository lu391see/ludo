package de.htwg.se.ludo
import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.aview.Tui
import model._

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {
    val fields = 72
    val maxPlayers = 4
    val totalPins = maxPlayers * 4

    val teams = Vector(
      Team('Y', 0, 16, 56),
      Team('G', 4, 26, 60),
      Team('R', 8, 36, 64),
      Team('B', 12, 46, 68)
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

    val board = new Board(fields, EmptyCell(), totalPins)
    var game = Game(board, players).base()
    val tui = new Tui
    val controller = new Controller(Game(new Field(40, Cell(0)), players))
    val tui = new Tui(controller)
    controller.notifyObservers()

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
      println(s"==> ${players(turnCounter).name} can walk ${dice.throwDice()} please choose a pin (1-4)!")

      input = readLine()
      if (input == "q") {
        return
      }
      if(!input.matches("[1-4]")) {
        input = readLine("No valid Pin, try again!\n")
      }
      game = tui.processInputLine(input, game, player, dice)
      tui.processInputLine(input, players(turnCounter), dice)

      turnCounter += 1
      turnCounter %= playerAmount
    }
  }
}