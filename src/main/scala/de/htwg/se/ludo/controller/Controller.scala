package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{AllPinWinStrategy, Cell, Board, Game, OnePinWinStrategy, Player, PlayerBuilder, RandomDice, Team}
import de.htwg.se.ludo.util.{Observable, UndoManager}

class Controller() extends Observable {
  var currentPlayer: Option[Player] = None
  var game: Option[Game] = None
  var gameState: GameState = GameState(this)
  var pips: Int = 0

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

  def execute(input: String): Boolean = {
    gameState.handle(input)
    true
  }

  def newGame(): Unit = {
    val board = new Board(fields, Cell(""), totalPins)
    game = Some(Game(board, players).spawnedPins())
    notifyObservers()
  }

  def gameToString: String = game match {
    case Some(g) => g.board.toString
    case None => "\nGame Board not yet initialized!\n"
  }

  def roll(): Unit = {
    // undoManager.doStep(new RollCommand(this))
    pips = RandomDice().pips
    currentPlayer match {
      case Some(c) => println(c + " throwed " + pips)
      case None =>
    }
  }

  def draw(pin: Int): Unit = {
    undoManager.doStep(new DrawCommand(pin, this))
  }

  def addPlayer(name: String, team: Team): Unit = {
    undoManager.doStep(new AddPlayerCommand(name, team, this))
  }

  def nextPlayer(): Unit = {
    currentPlayer match {
      case Some(c) => currentPlayer = Some(players((players.indexOf(c) + 1) % players.size))
      case None =>
    }
  }

  def setWinStrategy(winStrategy: String): Unit = {
   game match {
     case Some(g) => winStrategy match {
       case "one" => g.setWinStrategy(OnePinWinStrategy())
       case _ => g.setWinStrategy(AllPinWinStrategy())
     }
     case None => println("error: can not set win strategy at the beginning please try again!")

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
