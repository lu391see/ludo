package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{AllPinWinStrategy, Cell, Board, Game, OnePinWinStrategy, Player, PlayerBuilder, RandomDice, Team}
import de.htwg.se.ludo.util.{Observable, UndoManager}

class Controller() extends Observable {
  var currentPlayer: Player = _
  var game: Game = _
  var gameState: GameState = GameState(this)

  private val undoManager = new UndoManager

  var players: Vector[Player] = Vector.empty
  val fields = 72
  val maxPlayers = 4
  val totalPins: Int = maxPlayers * 4
  val teams = Vector(
    new Team('Y', 0, 16, 56),
    new Team('G', 4, 26, 60),
    new Team('R', 8, 36, 64),
    new Team('B', 12, 46, 68)
  )

  var pips = 0

  def execute(input: String): Boolean = {
    gameState.handle(input)
    true
  }

  def newGame(): Unit = {
    val board = new Board(fields, Cell(""), totalPins)
    game = Game(board, players).base()
    notifyObservers()
  }

  def gameToString: String = game.board.toString

  def roll(): Unit = {
    pips = RandomDice().pips
    println(currentPlayer + " throwed " + pips)
  }

  def draw(pin: Int): Unit = {
    undoManager.doStep(new DrawCommand(pin, this))
  }

  def addPlayer(name: String, team: Team): Unit = {
    undoManager.doStep(new AddPlayerCommand(name, team, this))
  }

  def nextPlayer(): Unit = {
    currentPlayer = players((players.indexOf(currentPlayer) + 1) % players.size)
  }

  def setWinStrategy(winStrategy: String): Unit = {
   if(gameState.state.getClass.getSimpleName == "SetupState") {
     throw new IllegalArgumentException("error: can not set win strategy at the beginning please try again!")
   }
    winStrategy match  {
      case "one" => game.setWinStrategy(OnePinWinStrategy())
      case _ => game.setWinStrategy(AllPinWinStrategy())
    }
  }

  def undo(): Unit = {
    undoManager.undoStep()
    //notifyObservers() this is only useful during the game, hence it's only used in DrawCommand
  }

  def redo(): Unit = {
    undoManager.redoStep()
    //notifyObservers()
  }
}
