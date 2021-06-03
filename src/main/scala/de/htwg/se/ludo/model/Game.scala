package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.WinStrategy

case class Game(board: Board, players: Vector[Player]) {
  var winStrategy: WinStrategy = AllPinWinStrategy()
  val emptyCell: Cell = Cell("")

  def setWinStrategy(winStrategy: WinStrategy): Unit = {
    println("changed strategy")
    this.winStrategy = winStrategy
  }

  def spawnedPins(): Game = {
    var changed = board
    players.foreach(player => {
      player.team.pins.foreach(pin => changed = renderedPin(pin.position, Cell(pin.id), changed))
    })
    changedGame(changed)
  }

  def renderedPin(pos: Int, cell: Cell, board: Board): Board = {
    board.replaceCell(pos, cell)
  }

  def draw(player: Player, pin: Int, dice_roll: Int): Game = {
    tryPinSpawn(player: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => return changed
      case None          =>
    }
    tryPinMove(player: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => changed
      case None          => this
    }
  }

  def tryPinSpawn(player: Player, pin: Int, dice_roll: Int): Option[Game] = {
    if (!player.team.isSpawned(pin)) {
      trySixRoll(player, dice_roll)
      if (player.sixRolled) {
        player.sixRolled = false
        return Some(spawnedPin(player, pin))
      }
    }
    None
  }

  def tryPinMove(player: Player, pin: Int, dice_roll: Int): Option[Game] = {
    if (player.team.isSpawned(pin) && !player.team.isFinished(pin)) {
      var changed = this
      tryEnemyPinBeat(player: Player, pin: Int, dice_roll: Int) match {
        case Some(c) => changed = c
        case None =>
      }
      println("moving")
      return Some(changed.drawnPin(player, pin, dice_roll))
    }
    None
  }

  def tryEnemyPinBeat(
      player: Player,
      pin: Int,
      dice_roll: Int
  ): Option[Game] = {
    if (hasEnemyPinAhead(player, pin, dice_roll)) {
      var changed = this
      println("beating enemy")
      val playerPin = player.team.pins(pin)
      val enemyPos = enemyPinPosition(playerPin, dice_roll)
      val enemyPlayer = findEnemyPlayerAtPosition(enemyPos).get
      val enemyPin = findEnemyPinAtPosition(enemyPlayer, enemyPos)
      val enemyPinId = enemyPlayer.team.pins(enemyPin).id
      enemyPlayer.team.basePin(enemyPin)
      changed = beatenEnemy(enemyPos)
      return Some(changedGame(renderedPin(enemyPlayer.team.pins(enemyPin).position, Cell(enemyPinId), changed.board)))
    }
    None
  }

  def findEnemyPlayerAtPosition(pos: Int): Option[Player] = {
    players.find(p => p.team.findPinAtPosition(pos) != -1)
  }

  def findEnemyPinAtPosition(enemyPlayer: Player, position: Int): Int = {
    enemyPlayer.team.findPinAtPosition(position)
  }

  def changedGame(board: Board): Game = {
    Game(board, players)
  }

  def beatenEnemy(enemyPos: Int): Game = {
    changedGame(board.replaceCell(enemyPos, emptyCell))
  }

  def hasEnemyPinAhead(player: Player, pin: Int, dice_roll: Int): Boolean = {
    val enemyPin = player.team.pins(pin)
    val potentialEnemyCell = board.cell(enemyPinPosition(enemyPin, dice_roll))
    potentialEnemyCell.isSet && potentialEnemyCell.getValue.charAt(
      0
    ) != player.team.color
  }

  def enemyPinPosition(pin: Pin, dice_roll: Int): Int = {
    pin.position + dice_roll
  }

  def trySixRoll(player: Player, dice_roll: Int): Unit = {
    if (dice_roll == 6) {
      player.sixRolled = true
    }
  }

  def spawnedPin(player: Player, pin: Int): Game = {
    val spawned = changedGame(
      board.replaceCell(player.team.position(pin), emptyCell)
    )
    player.spawn(pin)
    changedGame(
      spawned.board
        .replaceCell(player.team.position(pin), Cell(player.team.id(pin)))
    )
  }

  def drawnPin(player: Player, pin: Int, pos: Int): Game = {
    movedPin(player: Player, pin: Int, pos: Int) match {
      case Some(moved) => moved
      case None          => finishedPin(player, pin)
    }
  }

  def movedPin(player: Player, pin: Int, pos: Int): Option[Game] = {
    val pinPosition = player.team.position(pin)
    val newPos = pinPosition + pos
    if (newPos < player.team.homePosition) {
      val changed = changedGame(board.replaceCell(pinPosition, emptyCell))
      player.move(pin, newPos)
      return Some(
        changedGame(
          changed.board.replaceCell(newPos, Cell(player.team.id(pin)))
        )
      )
    }
    None
  }

  def finishedPin(player: Player, pin: Int): Game = {
    val pinPosition = player.team.position(pin)
    val changed =
      Game(board.replaceCell(pinPosition, emptyCell), players)
    player.finish(pin)
    changedGame(
      changed.board
        .replaceCell(pinPosition, Cell(player.team.id(pin)))
    )
  }
}
