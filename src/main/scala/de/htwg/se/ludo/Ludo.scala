package de.htwg.se.ludo
import model._

object Ludo {
  def main(args: Array[String]): Unit = {
    val playerarraysize = scala.io.StdIn.readLine(
      """
        |Welcome to Ludo aka 'Mensch Aergere Dich Nicht'!
        |How many players want to play?
        |Type between 1-4: """.stripMargin)

    val players:Array[Player] = new Array[Player](playerarraysize.toInt)
    var setup_str: String = "Hello"

    var player_counter: Int = 1
    for (i <- 0 until  playerarraysize.toInt) {
      val player_name: String = scala.io.StdIn.readLine(s"Player $player_counter, type your name: ")
      players(player_counter - 1) = Player(player_name, player_counter)
      setup_str += ", " + players(player_counter - 1).name
      player_counter += 1
    }
    player_counter -= 1

    val game = new Field[Cell](40, Cell(0))
    println(setup_str)

    var turn_counter = 0

    var input: String = ""

    while(input != "q") {
      val dice = Dice()
      println(s"""
            |Current Game Status:
            |${game.toString}
            |==> ${players(turn_counter).name} can walk ${dice.t1} please choose a pin (1-4)!""".stripMargin)

      input = scala.io.StdIn.readLine()
      // TODO: input needs to be processed

      turn_counter += 1
      if(turn_counter == player_counter) {
        turn_counter = turn_counter - player_counter
      }

    }
  }
}