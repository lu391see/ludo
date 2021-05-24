package de.htwg.se.ludo.model

case class PlayerSpawn(board: Board, players: Vector[Player]) {
  def basedPins(): Board = {
    var changed = board
    players.foreach(player => {
      player.team.pins.foreach(pin =>
        changed = renderedPin(pin.position, Cell(pin.id), changed)
      )
    })
    changed
  }

  def spawnedPin(player: Player, pin: Int): Board = {
    val spawned = board.replaceCell(player.team.position(pin), EmptyCell)
    player.spawn(pin)
    spawned.replaceCell(player.team.position(pin), Cell(player.team.id(pin)))
  }

  private def renderedPin(pos: Int, cell: Cell, board: Board): Board = {
    board.replaceCell(pos, cell)
  }
}
case class PlayerMovement(board: Board) {
  def movedPin(player: Player, pin: Int, pos: Int): Option[Board] = {
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
        changed.replaceCell(newPos, Cell(player.team.id(pin)))
      )
    }
    None
  }
}
case class PlayerFight(board: Board, players: Vector[Player]) {
  def beatenEnemyPin(
      player: Player,
      pin: Int,
      dice_roll: Int
  ): Option[Board] = {
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
}
case class PlayerFinish(board: Board, players: Vector[Player]) {
  def finishedPin(player: Player, pin: Int): Board = {
    val pinPosition = player.team.position(pin)
    val changed =
      board.replaceCell(pinPosition, EmptyCell)
    player.finish(pin)
    changed
      .replaceCell(player.team.position(pin), Cell(player.team.id(pin)))
  }
}
