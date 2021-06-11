package de.htwg.se.ludo.aview

import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{RollState, SetupState}
import de.htwg.se.ludo.model.diceComponent.dice6Impl.Dice
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.{Cell, EmptyCell}
import de.htwg.se.ludo.util.AddAnotherPlayerMessage
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
  "A Ludo TUI" should {

    val controller = new Controller()
    val tui = new TUI(controller)

    val mockdiceFour = Dice(4)
    val mockdiceSix = Dice(6)

    "initially add players to the game " in {
      controller.gameState.state should be(SetupState(controller))
      tui.processInput("lukas")
    }
    "at least two" in {
      tui.processInput("start")
      controller.message should be(AddAnotherPlayerMessage)
      tui.processInput("alex")
      controller.players.size should be(2)
      controller.players(0).name should be("lukas")
      controller.players(1).name should be("alex")
    }
    "with undo and redo to remove players" in {
      tui.processInput("z")
      controller.players.size should be(1)

      tui.processInput("hans")
      tui.processInput("y")

      controller.players.size should be(3)
      controller.players.foreach(p => p.name matches "alex|lukas|hans")
    }
    "until start is pressed or 4 players are added" in {
      tui.processInput("start")
      controller.gameState.state should be(RollState(controller))
    }
    "switch between win conditions without changing the games current state" in {
      val currentState = controller.gameState.state

      tui.processInput("one")
      controller.winStrategy should be(OnePinWinStrategy())
      controller.gameState.state should be(currentState)

      tui.processInput("all")
      controller.winStrategy should be(AllPinWinStrategy())
      controller.gameState.state should be(currentState)
    }
    "don't move a selected pin from base when rolled a four without previous six" in {
      tui.processInput("any input for dice throw")
      controller.rollDice(mockdiceFour)
      controller.pips should be(4)

      tui.processInput("1")

      controller.game should be(defined)
      controller.game.get.board.spots(controller.players(0).team.basePosition) should be(Cell(controller.players(0).team.pinID(0)))
      controller.game.get.board.spots(controller.players(0).team.startPosition) should be(EmptyCell)

    }
    "switch player and move a pin from base to start field when rolled a 6" in {
      controller.currentPlayer should be(defined)
      controller.currentPlayer.get should be(controller.players(1))

      tui.processInput("any input for dice throw")
      controller.rollDice(mockdiceSix)
      controller.pips should be(6)

      tui.processInput("3")
      val base_pin3 = controller.players(1).team.basePosition + 2
      val id_pin3 = controller.currentPlayer.get.team.pinID(2)

      controller.game should be (defined)
      controller.game.get.board.spots(base_pin3) should be(EmptyCell)
      controller.game.get.board.spots(controller.players(1).team.startPosition) should be(Cell(id_pin3))

    }
  }
}
