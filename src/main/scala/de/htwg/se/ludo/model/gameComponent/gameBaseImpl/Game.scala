package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import de.htwg.se.ludo.model.gameComponent.{
  BoardInterface,
  CellInterface,
  GameInterface
}
import de.htwg.se.ludo.model.playerComponent.{Pin, Player}

case class Game(board: BoardInterface, players: Vector[Player])
    extends GameInterface {
  val EmptyCell: CellInterface = Cell("")

  def based(): GameInterface = {
    changedGame(basedPins())
  }

  private def basedPins(): BoardInterface = {
    var changed = board
    players.foreach(player => {
      player.team.pins.foreach(pin => {
        println(pin.number)
        changed = changed
          .replaceCell(player.team.basePosition + pin.number - 1, Cell(pin.id))
      })
    })
    changed
  }

  def draw(currentPlayer: Player, pinNumber: Int, steps: Int): GameInterface = {
    val pinPosition = findPinPosition(currentPlayer, pinNumber)

    tryPinSpawn(currentPlayer, pinPosition, steps) match {
      case Some(changed) => return changed
      case None          =>
    }
    tryPinMove(currentPlayer: Player, pinPosition: Int, steps: Int) match {
      case Some(changed) => changed
      case None          => this
    }
  }

  def findPinPosition(currentPlayer: Player, pinNumber: Int): Int = {
    val pin = currentPlayer.team.pins(pinNumber - 1)
    board.spots.indexWhere(cell => {
      cell.isSet && cell.value == pin.id
    })

  }

  private def tryPinSpawn(
      player: Player,
      pinPosition: Int,
      dice_roll: Int
  ): Option[GameInterface] = {
    if (!pinIsSpawned(pinPosition, player.team.homePosition)) {
      trySixRoll(player, dice_roll)
      if (player.sixRolled) {
        player.sixRolled = false
        return Some(changedGame(spawnedPin(player, pinPosition)))
      }
    }
    None
  }

  private def pinIsSpawned(pinPosition: Int, teamHomePosition: Int): Boolean = {
    pinPosition >= board.baseSize && pinPosition < teamHomePosition
  }

  private def trySixRoll(player: Player, dice_roll: Int): Unit = {
    if (dice_roll >= 6) {
      player.sixRolled = true
    }
  }

  private def spawnedPin(player: Player, pinPosition: Int): BoardInterface = {
    val currentSpot = board.spots(pinPosition)
    board
      .replaceCell(pinPosition, EmptyCell)
      .replaceCell(player.team.startPosition, currentSpot)
  }

  private def tryPinMove(
      player: Player,
      pinPosition: Int,
      steps: Int
  ): Option[GameInterface] = {

    if (pinIsSpawned(pinPosition, player.team.homePosition)) {
      var changed: GameInterface = this
      tryEnemyPinBeat(player.team.toColorString, pinPosition, steps) match {
        case Some(c) => changed = c
        case None    =>
      }
      return Some(changed.drawnPin(player, pinPosition, steps))
    }
    None
  }

  private def tryEnemyPinBeat(
      currentPlayerColor: Char,
      pinPosition: Int,
      steps: Int
  ): Option[GameInterface] = {

    beatenEnemyPin(currentPlayerColor, pinPosition, steps) match {
      case Some(beaten) => Some(changedGame(beaten))
      case None         => None
    }
  }

  private def beatenEnemyPin(
      currentPlayerColor: Char,
      pinPosition: Int,
      steps: Int
  ): Option[BoardInterface] = {
    val enemyPinPosition = potentialEnemyPinPosition(pinPosition, steps)

    if (hasEnemyPinAhead(enemyPinPosition, currentPlayerColor)) {
      val enemySpot = board.spots(enemyPinPosition)
      val enemyColor = enemySpot.color
      val enemyPinNumber = enemySpot.pinNumber
      val enemyBasePosition = players
        .find(p => p.team.toColorString == enemyColor)
        .get
        .team
        .basePosition + enemyPinNumber - 1
      return Some(
        board
          .replaceCell(enemyPinPosition, EmptyCell)
          .replaceCell(enemyBasePosition, enemySpot)
      )
    }
    None
  }

  private def potentialEnemyPinPosition(pinPosition: Int, steps: Int): Int = {
    var enemyPos = pinPosition + steps
    if (enemyPos >= board.gameSize)
      enemyPos = (enemyPos % board.gameSize) + board.baseSize
    enemyPos
  }

  private def hasEnemyPinAhead(
      potentialEnemyPosition: Int,
      playerColor: Char
  ): Boolean = {
    val potentialEnemySpot = board.spots(potentialEnemyPosition)
    potentialEnemySpot.isSet && potentialEnemySpot.color != playerColor
  }

  def drawnPin(player: Player, pinPosition: Int, steps: Int): GameInterface = {
    val movePos = movePosition(pinPosition, steps)
    if (movePos == player.team.startPosition) {
      return changedGame(finishedPin(pinPosition, player.team.homePosition))
    }
    changedGame(movedPin(pinPosition, movePos))
  }

  private def movedPin(
      pinPosition: Int,
      movePosition: Int
  ): BoardInterface = {
    val currentSpot = board.spots(pinPosition)
    board
      .replaceCell(pinPosition, EmptyCell)
      .replaceCell(movePosition, currentSpot)
  }

  private def movePosition(pinPosition: Int, steps: Int): Int = {
    var movePos = pinPosition + steps
    if (movePos >= board.gameSize) {
      movePos = (movePos % board.gameSize) + board.baseSize
    }
    movePos
  }

  private def changedGame(board: BoardInterface): GameInterface = {
    Game(board, players)
  }

  private def finishedPin(
      pinPosition: Int,
      teamHomePosition: Int
  ): BoardInterface = {
    val currentSpot = board.spots(pinPosition)
    val pinNumber = currentSpot.pinNumber
    val homePosition = teamHomePosition + pinNumber - 1
    board
      .replaceCell(pinPosition, EmptyCell)
      .replaceCell(homePosition, currentSpot)
  }

}
