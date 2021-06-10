package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.commands._
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.GameState
import de.htwg.se.ludo.controller.{NewGame, NewMessage, NewPlayer, PinDrawn, Redo, Undo}
import de.htwg.se.ludo.model.diceComponent.DiceInterface
import de.htwg.se.ludo.model.playerComponent.{Player, PlayerConstraints}
import de.htwg.se.ludo.model.gameComponent.{BoardInterface, GameInterface}
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.{BasicBoardConstraints, Board, Game}
import de.htwg.se.ludo.util._


class Controller extends ControllerInterface {

  var currentPlayer: Option[Player] = None
  var game: Option[GameInterface] = None
  var gameState: GameState = GameState(this)
  var pips: Int = 0
  var players: Vector[Player] = Vector.empty
  var message: Message = EmptyMessage

  private val undoManager = new UndoManager

  def handleInput(input: String): Unit = {
    gameState.handle(input)
  }

  def newGame(): Unit = {
    val board: BoardInterface = new Board(
      BasicBoardConstraints.fields,
      BasicBoardConstraints.filling,
      PlayerConstraints.totalPins
    )
    game = Some(Game(board, players).based())
    publish(NewGame())
  }

  def addNewPlayer(name: String): Unit = {
    undoManager.doStep(
      new AddPlayerCommand(name, PlayerConstraints.teams(players.size), this)
    )
    publish(NewPlayer())
  }

  def switchPlayer(): Unit = {
    currentPlayer match {
      case Some(c) =>
        currentPlayer = Some(players((players.indexOf(c) + 1) % players.size))
        newMessage(NextPlayerMessage(currentPlayer.get))
      case None => newMessage(NoCurrentPlayerMessage)
    }
  }

  def rollDice(dice: DiceInterface): Unit = {
    pips = dice.pips
    newMessage(currentPlayer match {
      case Some(c) => PlayerRolledDiceMessage(c, pips)
      case None    => NoCurrentPlayerMessage
    })
  }

  def drawPin(pin: Int): Unit = {
    val curPos = currentPlayer.get.team.position(pin)
    undoManager.doStep(new DrawCommand(pin, this))
    val nextPos = currentPlayer.get.team.position(pin)
    publish(
      PinDrawn(
        color = currentPlayer.get.team.color,
        pinId = pin+1,
        curPos,
        nextPos
      )
    )
  }

  def undo(): Unit = {
    undoManager.undoStep()
    publish(Undo())
  }

  def redo(): Unit = {
    undoManager.redoStep()
    publish(Redo())
  }

  def newMessage(message: Message): Unit = {
    this.message = message
    publish(NewMessage())
  }

  def publishMessage(): Unit = {
    this.message.print()
  }

  def messageToString(): String = {
    this.message.toString
  }

  def setWinStrategy(winStrategy: String): Unit = {}

  override def toString: String =
    "Current game status: " + (game match {
      case Some(g) => g.board.toString
      case None    => GameBoardUninitializedMessage.toString
    })
}
