package de.htwg.se.ludo
import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.aview.TUI

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {

    val controller = new Controller()
    val tui = new TUI(controller)

    var input: String = ""

    println(
      """|Welcome to Ludo aka 'Mensch Aergere Dich Nicht'!
        |Type up to 4 player names and start with 'start'
        |Exit at any time with 'q'""".stripMargin
    )

    while (true) {
      input = readLine()
      if (input == "q") {return}
      tui.processInput(input)
    }
  }
}