package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{
  Board,
  BoardConstraints,
  EmptyMessage,
  Game,
  GameBoardUninitializedMessage,
  Message,
  NextPlayerMessage,
  NoCurrentPlayerMessage,
  Player,
  PlayerConstraints,
  PlayerRolledDiceMessage,
  RandomDice
}
import de.htwg.se.ludo.util.UndoManager

import scala.swing.Publisher

class Controller extends Publisher {
  var currentPlayer: Option[Player] = None
  var game: Option[Game] = None
  var gameState: GameState = GameState(this)
  var pips: Int = 0
  var players: Vector[Player] = Vector.empty
  var message: Message = EmptyMessage

  private val undoManager = new UndoManager

  def handleInput(input: String): Unit = {
    gameState.handle(input)
  }

  def newGame(): Unit = {
    val board = new Board(
      BoardConstraints.fields,
      BoardConstraints.filling,
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

  def rollDice(): Unit = {
    pips = RandomDice().pips
    newMessage(currentPlayer match {
      case Some(c) => PlayerRolledDiceMessage(c, pips)
      case None    => NoCurrentPlayerMessage
    })
  }

  def drawPin(pin: Int): Unit = {
    undoManager.doStep(new DrawCommand(pin, this))
    publish(PinDrawn())
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

  def setWinStrategy(winStrategy: String): Unit = {}

  override def toString: String =
    "Current game status: " + (game match {
      case Some(g) => g.board.toString
      case None    => GameBoardUninitializedMessage.toString
    })
}
