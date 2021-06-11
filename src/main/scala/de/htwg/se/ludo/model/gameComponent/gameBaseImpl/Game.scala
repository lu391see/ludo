package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import de.htwg.se.ludo.model.gameComponent.{BoardInterface, GameInterface}
import de.htwg.se.ludo.model.playerComponent.{Pin, Player}

case class Game(board: BoardInterface, players: Vector[Player]) extends GameInterface {

  def based(): GameInterface = {
    changedGame(basedPins())
  }

  private def basedPins(): BoardInterface = {
    var changed = board
    players.foreach(player => {
      player.team.pins.foreach(pin =>
        changed = changed.replaceCell(pin.position, Cell(pin.id))
      )
    })
    changed
  }

  def draw(currentPlayer: Player, pin: Int, dice_roll: Int): GameInterface = {
    tryPinSpawn(currentPlayer: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => return changed
      case None          =>
    }
    tryPinMove(currentPlayer: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => changed
      case None          => this
    }
  }

  def drawnPin(player: Player, pin: Int, pos: Int): GameInterface = {

    movedPin(player: Player, pin: Int, pos: Int) match {
      case Some(moved) => changedGame(moved)
      case None => changedGame(finishedPin(player, pin))
    }
  }

  private def finishedPin(player: Player, pin: Int): BoardInterface = {
    val pinPosition = player.team.position(pin)
    val changed =
      board.replaceCell(pinPosition, EmptyCell)
    player.finish(pin)
    changed
      .replaceCell(player.team.position(pin), Cell(player.team.pinID(pin)))
  }

  private def movedPin(player: Player, pin: Int, pos: Int): Option[BoardInterface] = {
    val pinPosition = player.team.position(pin)
    var newPos = pinPosition + pos
    if (newPos >= board.gameSize - 1) {
      newPos = (newPos % board.gameSize) + board.baseSize

      if (newPos == player.team.startPosition) {
        return None
      }
    }
    if (newPos < board.gameSize) {
      val changed = board.replaceCell(pinPosition, EmptyCell)
      player.move(pin, newPos)
      return Some(
        changed.replaceCell(newPos, Cell(player.team.pinID(pin)))
      )
    }
    None
  }

  private def tryPinSpawn(player: Player, pin: Int, dice_roll: Int): Option[GameInterface] = {

    if (!player.team.isSpawned(pin)) {
      trySixRoll(player, dice_roll)
      if (player.sixRolled) {
        player.sixRolled = false
        return Some(changedGame(spawnedPin(player, pin)))
      }
    }
    None
  }

  private def spawnedPin(player: Player, pin: Int): BoardInterface = {
    val spawned = board.replaceCell(player.team.position(pin), EmptyCell)
    player.spawn(pin)
    spawned.replaceCell(player.team.position(pin), Cell(player.team.pinID(pin)))
  }

  private def tryPinMove(player: Player, pin: Int, dice_roll: Int): Option[GameInterface] = {

    if (player.team.isSpawned(pin) && !player.team.isFinished(pin)) {
      var changed: GameInterface = this
      tryEnemyPinBeat(player: Player, pin: Int, dice_roll: Int) match {
        case Some(c) => changed = c
        case None    =>
      }
      return Some(changed.drawnPin(player, pin, dice_roll))
    }
    None
  }

  private def tryEnemyPinBeat(player: Player, pin: Int, dice_roll: Int): Option[GameInterface] = {

    beatenEnemyPin(player, pin, dice_roll) match {
      case Some(beaten) => Some(changedGame(beaten))
      case None         => None
    }
  }

  private def beatenEnemyPin(
                      player: Player,
                      pin: Int,
                      dice_roll: Int
                    ): Option[BoardInterface] = {
    if (hasEnemyPinAhead(player, pin, dice_roll)) {
      val playerPin = player.team.pins(pin)
      val enemyPos = enemyPinPosition(playerPin, dice_roll)
      val enemyPlayer = findEnemyPlayerAtPosition(enemyPos).get
      val enemyPin = findEnemyPinAtPosition(enemyPlayer, enemyPos)
      val enemyPinId = enemyPlayer.team.pins(enemyPin).id
      enemyPlayer.team.basePin(enemyPin)
      val changed = board.replaceCell(enemyPos, EmptyCell)
      return Some(
        changed.replaceCell(
          enemyPlayer.team.pins(enemyPin).position,
          Cell(enemyPinId)
        )
      )
    }
    None
  }

  private def findEnemyPlayerAtPosition(pos: Int): Option[Player] = {
    players.find(p => p.team.findPinAtPosition(pos) != -1)
  }

  private def findEnemyPinAtPosition(enemyPlayer: Player, position: Int): Int = {
    enemyPlayer.team.findPinAtPosition(position)
  }

  private def hasEnemyPinAhead(player: Player, pin: Int, dice_roll: Int): Boolean = {
    val enemyPin = player.team.pins(pin)
    val potentialEnemyCell = board.cell(enemyPinPosition(enemyPin, dice_roll))
    potentialEnemyCell.isSet && potentialEnemyCell.getValue.charAt(
      0
    ) != player.team.color
  }

  private def enemyPinPosition(pin: Pin, dice_roll: Int): Int = {
    var enemyPos = pin.position + dice_roll
    if (enemyPos >= board.gameSize)
      enemyPos = (enemyPos % board.gameSize) + board.baseSize
    enemyPos
  }

  private def changedGame(board: BoardInterface): GameInterface = {
    Game(board, players)
  }

  private def trySixRoll(player: Player, dice_roll: Int): Unit = {
    if (dice_roll >= 6) {
      player.sixRolled = true
    }
  }
}
