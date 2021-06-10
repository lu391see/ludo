package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.boardComponent.boardBaseImpl.{Board, PlayerFight, PlayerFinish, PlayerMovement, PlayerSpawn}
import de.htwg.se.ludo.model.playerComponent.Player

case class Game(board: Board, players: Vector[Player]) {
  var playerSpawn: PlayerSpawn = PlayerSpawn(board, players)
  var playerMovement: PlayerMovement = PlayerMovement(board)
  var playerFight: PlayerFight = PlayerFight(board, players)
  var playerFinish: PlayerFinish = PlayerFinish(board, players)

  def based(): Game = {
    playerSpawn = PlayerSpawn(board, players)
    changedGame(playerSpawn.basedPins())
  }

  def draw(currentPlayer: Player, pin: Int, dice_roll: Int): Game = {
    tryPinSpawn(currentPlayer: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => return changed
      case None          =>
    }
    tryPinMove(currentPlayer: Player, pin: Int, dice_roll: Int) match {
      case Some(changed) => changed
      case None          => this
    }
  }

  private def tryPinSpawn(
      player: Player,
      pin: Int,
      dice_roll: Int
  ): Option[Game] = {
    if (!player.team.isSpawned(pin)) {
      trySixRoll(player, dice_roll)
      if (player.sixRolled) {
        player.sixRolled = false
        playerSpawn = PlayerSpawn(board, players)
        return Some(changedGame(playerSpawn.spawnedPin(player, pin)))
      }
    }
    None
  }

  private def tryPinMove(
      player: Player,
      pin: Int,
      dice_roll: Int
  ): Option[Game] = {
    if (player.team.isSpawned(pin) && !player.team.isFinished(pin)) {
      var changed = this
      tryEnemyPinBeat(player: Player, pin: Int, dice_roll: Int) match {
        case Some(c) => changed = c
        case None    =>
      }
      return Some(changed.drawnPin(player, pin, dice_roll))
    }
    None
  }

  private def tryEnemyPinBeat(
      player: Player,
      pin: Int,
      dice_roll: Int
  ): Option[Game] = {
    playerFight = PlayerFight(board, players)
    playerFight.beatenEnemyPin(player, pin, dice_roll) match {
      case Some(beaten) => Some(changedGame(beaten))
      case None         => None
    }
  }

  private def drawnPin(player: Player, pin: Int, pos: Int): Game = {
    playerMovement = PlayerMovement(board)
    playerMovement.movedPin(player: Player, pin: Int, pos: Int) match {
      case Some(moved) => changedGame(moved)
      case None =>
        playerFinish = PlayerFinish(board, players)
        changedGame(playerFinish.finishedPin(player, pin))
    }
  }

  private def changedGame(board: Board): Game = {
    Game(board, players)
  }

  private def trySixRoll(player: Player, dice_roll: Int): Unit = {
    if (dice_roll == 6) {
      player.sixRolled = true
    }
  }
}
