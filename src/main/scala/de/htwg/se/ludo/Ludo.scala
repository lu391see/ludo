package de.htwg.se.ludo
import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.aview.Tui
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

    val controller = new Controller(Game(new Field(40, Cell(0)), players))
    val tui = new Tui(controller)
    controller.notifyObservers()

    var turnCounter = 0

    var input: String = ""

    while(true) {
      val dice = RandomDice()
      println(s"==> ${players(turnCounter).name} can walk ${dice.throwDice()} please choose a pin (1-4)!")

      input = readLine()
      if (input == "q") {
        return
      }
      tui.processInputLine(input, players(turnCounter), dice)

      turnCounter += 1
      turnCounter %= playerAmount
    }
  }
}