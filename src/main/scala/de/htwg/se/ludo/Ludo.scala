package de.htwg.se.ludo
import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.aview.TUI
import de.htwg.se.ludo.util.UI

import scala.io.StdIn.readLine

object Ludo {

  def main(args: Array[String]): Unit = {

    val controller = new Controller()
    val ui: UI = new TUI(controller)

    var input: String = ""

    println(
      """|Welcome to Ludo aka 'Mensch Aergere Dich Nicht'!
        |Type up to 4 player names and start with 'start'
        |Change the win strategy at any time with 'one' for a one pin win strategy or 'all' for an all pin win strategy
        |Use z at any time to undo a step
        |Use y at any time to redo a step
        |Exit at any time with 'q'""".stripMargin
    )

    while (true) {
      input = readLine()
      if (input == "q") {return}
      ui.processInput(input)
    }
  }
}