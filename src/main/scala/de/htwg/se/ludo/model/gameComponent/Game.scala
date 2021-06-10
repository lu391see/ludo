package de.htwg.se.ludo.model.gameComponent

import de.htwg.se.ludo.model.boardComponent.BoardInterface
import de.htwg.se.ludo.model.playerComponent.Player

case class Game(board: BoardInterface, players: Vector[Player]) extends GameInterface {
  var playerSpawn: PlayerSpawn = PlayerSpawn(board, players)
  var playerMovement: PlayerMovement = PlayerMovement(board)
  var playerFight: PlayerFight = PlayerFight(board, players)
  var playerFinish: PlayerFinish = PlayerFinish(board, players)

  def based(): GameInterface = {
    playerSpawn = PlayerSpawn(board, players)
    changedGame(playerSpawn.basedPins())
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

    playerMovement = PlayerMovement(board)
    playerMovement.movedPin(player: Player, pin: Int, pos: Int) match {
      case Some(moved) => changedGame(moved)
      case None =>
        playerFinish = PlayerFinish(board, players)
        changedGame(playerFinish.finishedPin(player, pin))
    }
  }

  private def tryPinSpawn(player: Player, pin: Int, dice_roll: Int): Option[GameInterface] = {

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

    playerFight = PlayerFight(board, players)
    playerFight.beatenEnemyPin(player, pin, dice_roll) match {
      case Some(beaten) => Some(changedGame(beaten))
      case None         => None
    }
  }

  private def changedGame(board: BoardInterface): GameInterface = {
    Game(board, players)
  }

  private def trySixRoll(player: Player, dice_roll: Int): Unit = {
    if (dice_roll == 6) {
      player.sixRolled = true
    }
  }
}
