package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Board, BoardConstraints, Game, GameBoardUninitializedMessage, NextPlayerMessage, NoCurrentPlayerMessage, Player, PlayerConstraints, PlayerRolledDiceMessage, RandomDice, Team}
import de.htwg.se.ludo.util.{Observable, UndoManager}

class Controller() extends Observable {
  var currentPlayer: Option[Player] = None
  var game: Option[Game] = None
  var gameState: GameState = GameState(this)
  var pips: Int = 0
  var winStrategy: WinStrategy = AllPinWinStrategy()

  private val undoManager = new UndoManager

  var players: Vector[Player] = Vector.empty

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
    notifyObservers()
  }

  def addNewPlayer(name: String): Unit = {
    undoManager.doStep(new AddPlayerCommand(name, PlayerConstraints.teams(players.size), this))
  }

  def switchPlayer(): Unit = {
    currentPlayer match {
      case Some(c) =>
        currentPlayer = Some(players((players.indexOf(c) + 1) % players.size))
        NextPlayerMessage(currentPlayer.get).print()
      case None => NoCurrentPlayerMessage.print()
    }
  }

  def rollDice(): Unit = {
    pips = RandomDice().pips
    currentPlayer match {
      case Some(c) => PlayerRolledDiceMessage(c, pips).print()
      case None    => NoCurrentPlayerMessage.print()
    }
  }

  def drawPin(pin: Int): Unit = {
  def isWon: Boolean = {
    winStrategy.hasWon(currentPlayer.get)
  }

  def draw(pin: Int): Unit = {
    undoManager.doStep(new DrawCommand(pin, this))
  }

  def addPlayer(name: String, team: Team): Unit = {
    undoManager.doStep(new AddPlayerCommand(name, team, this))
  }

  def nextPlayer(): Unit = {
    currentPlayer match {
      case Some(c) =>
        currentPlayer = Some(players((players.indexOf(c) + 1) % players.size))
      case None =>
    }
  }

  def setWinStrategy(strategy: String): Unit = {
    game match {
      case Some(_) =>
        strategy match {
          case "one" => winStrategy = OnePinWinStrategy()
          case _     => winStrategy = AllPinWinStrategy()
        }
      case None =>
        println(
          "error: can not set win strategy at the beginning please try again!"
        )
    }
  }

  def undo(): Unit = {
    undoManager.undoStep()
  }

  def redo(): Unit = {
    undoManager.redoStep()
  }

  def setWinStrategy(winStrategy: String): Unit = {}

  override def toString: String =
    game match {
      case Some(g) => g.board.toString
      case None    => GameBoardUninitializedMessage.toString
    }
}
