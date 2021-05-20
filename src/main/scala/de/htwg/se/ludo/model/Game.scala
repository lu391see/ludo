package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.WinStrategy

case class Game(board: Board, players: Vector[Player]) {
  var winStrategy: WinStrategy = AllPinWinStrategy()
  val emptyCell: Cell = Cell("")

  def setWinStrategy(winStrategy: WinStrategy): Unit = {
    println("changed strategy")
    this.winStrategy = winStrategy
  }

  def base(): Game = {
    var changed = board
    players.foreach(player => {
     player.team.pins.foreach(pin => changed = changed.replaceCell(pin.position, new Cell(pin.id)))
    })
    Game(changed, players)
  }

  def draw(player: Player, pin: Int, dice_roll: Int): Game = {
    if (!player.team.isSpawned(pin)) {
      trySix(player, dice_roll)
      if (player.successfulSixRoll) {
        player.successfulSixRoll = false
        return spawn(player, pin)
      }
    } else if (!player.team.isFinished(pin)) {
      var changed = this
      if (hasEnemyPinAhead(player, pin, dice_roll)) {
        println("beating enemy")
        changed = beat(enemyPinPosition(player, pin, dice_roll))
      }
      println("moving")
      return changed.move(player, pin, dice_roll)
    }
    this
  }

  def beat(pos: Int): Game = {
    Game(board.replaceCell(pos, Cell("")), players)
  }

  def hasEnemyPinAhead(player: Player, pin: Int, dice_roll: Int): Boolean = {
    val potentialEnemyCell =
      board.cell(enemyPinPosition(player, pin, dice_roll))
    potentialEnemyCell.isSet && potentialEnemyCell.getValue.charAt(
      0
    ) != player.team.color
  }

  def enemyPinPosition(player: Player, pin: Int, dice_roll: Int): Int = {
    player.team.position(pin) + dice_roll
  }

  def trySix(player: Player, dice_roll: Int): Unit = {
    if (dice_roll == 6) {
      player.successfulSixRoll = true
    }
  }

  def spawn(player: Player, pin: Int): Game = {
    val spawned = Game(board.replaceCell(player.team.position(pin), emptyCell), players)
    player.spawn(pin)
    Game(spawned.board.replaceCell(player.team.position(pin), new Cell(player.team.id(pin))), players)
  }

  def move(player: Player, pin: Int, pos: Int): Game = {
    val pinPosition = player.team.position(pin)
    val newPos = pinPosition + pos
    if (newPos < player.team.homePosition) {
      val changed = Game(board.replaceCell(pinPosition, emptyCell), players)

      player.move(pin, newPos)
      Game(changed.board.replaceCell(newPos, new Cell(player.team.id(pin))), players)
    } else {
      finish(player, pin)
    }
  }

  def finish(player: Player, pin: Int): Game = {
    val changed = Game(board.replaceCell(player.team.position(pin), emptyCell), players)
    player.finish(pin)
    Game(changed.board.replaceCell(player.team.position(pin), new Cell(player.team.id(pin))), players)
  }
}
