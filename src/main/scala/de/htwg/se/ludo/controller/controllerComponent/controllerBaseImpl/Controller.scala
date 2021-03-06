package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import com.google.inject.name.Names
import com.google.inject.{Guice, Inject, Injector}
import de.htwg.se.ludo.LudoModule
import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  NewGame,
  NewMessage,
  NewPlayer,
  PinDrawn,
  Redo,
  Undo
}
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.commands._
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{
  GameState,
  RollState
}
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.Game
import de.htwg.se.ludo.model.diceComponent.DiceInterface
import de.htwg.se.ludo.model.playerComponent.{Player, PlayerConstraints, Team}
import de.htwg.se.ludo.model.gameComponent.{
  BoardInterface,
  CellInterface,
  GameInterface
}
import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.util._
import net.codingwell.scalaguice.InjectorExtensions.ScalaInjector

class Controller @Inject() () extends ControllerInterface {

  private val undoManager = new UndoManager
  val injector: Injector = Guice.createInjector(new LudoModule)
  val fileIo: FileIOInterface = injector.instance[FileIOInterface]

  val EmptyCell: CellInterface =
    injector.instance[CellInterface](Names.named("EmptyCell"))
  var winStrategy: WinStrategy =
    injector.instance[WinStrategy](Names.named("OnePin"))

  var currentPlayer: Option[Player] = None
  var game: Option[GameInterface] = None
  var gameState: GameState = GameState(this)
  var pips: Int = 0
  var players: Vector[Player] = Vector.empty
  var message: Message = EmptyMessage
  val teams = PlayerConstraints.teams

  def handleInput(input: String): Unit = {
    gameState.handle(input)
  }

  def newGame(): Unit = {
    val board: BoardInterface = injector.instance[BoardInterface]
    this.game = Some(Game(board, players).based())
    publish(NewGame())
  }

  def addNewPlayer(name: String): Unit = {
    undoManager.doStep(
      new AddPlayerCommand(name, teams(players.size), this)
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
    pips = getDice.throwing
    newMessage(currentPlayer match {
      case Some(c) => PlayerRolledDiceMessage(c, pips)
      case None    => NoCurrentPlayerMessage
    })
  }

  def getDice: DiceInterface = {
    injector.instance[DiceInterface]
  }

  def drawPin(pin: Int): Unit = {
    val oldBoard = game.get.board
    undoManager.doStep(new DrawCommand(pin, this))
    val newBoard = game.get.board
    publish(
      PinDrawn(
        oldBoard = oldBoard,
        newBoard = newBoard
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

  def isWon: Boolean = {
    winStrategy.hasWon(currentPlayer.get, game.get.board)
  }

  def shouldNotDraw: Boolean = {
    (1 to 4).forall(pinNumber => {
      val pos = game.get.board.spots.indexWhere(spot => {
        spot.isSet && spot.pinNumber == pinNumber && spot.color == currentPlayer.get.team.toColorString
      })
      pos >= game.get.board.gameSize || pos < game.get.board.baseSize
    }) && pips != getDice.pips
  }

  def canNotDrawWithThisPin(pinNumber: Int): Boolean = {
    val pos = game.get.board.spots.indexWhere(spot => {
      spot.isSet && spot.pinNumber == pinNumber && spot.color == currentPlayer.get.team.toColorString
    })
    pos < game.get.board.baseSize && pips != getDice.pips
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

  def pinAlreadyFinished(pinNumber: Int): Boolean = {
    val pos = game.get.board.spots.indexWhere(spot => {
      spot.isSet && spot.pinNumber == pinNumber
    })
    pos >= game.get.board.gameSize
  }

  def setWinStrategy(winStrategy: WinStrategy): Unit = {
    this.winStrategy = winStrategy
  }

  override def toString: String =
    "Current game status: " + (game match {
      case Some(g) => g.board.toString
      case None    => GameBoardUninitializedMessage.toString
    })

  def isFinishedPin(pinNumber: Int): Boolean = {
    game.get
      .findPinPosition(currentPlayer.get, pinNumber) >= game.get.board.gameSize
  }

  override def save(): Unit = {
    fileIo.save(currentPlayer.get, game.get)
  }

  override def load(): Unit = {
    val (game, currentPlayerIndex) = fileIo.load()
    this.game = Some(game)
    players = game.players
    currentPlayer = Some(players(currentPlayerIndex))
    println(toString)
    newMessage(RollDiceMessage)
    gameState.state = RollState(this)

  }
}
