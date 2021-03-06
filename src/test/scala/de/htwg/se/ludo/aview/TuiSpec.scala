package de.htwg.se.ludo.aview

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{DrawState, RollState, SetupState}
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.Cell
import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}
import de.htwg.se.ludo.util.{AddAnotherPlayerMessage, ChoosePinMessage, RollDiceMessage, WelcomeMessage}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class TuiSpec extends AnyWordSpec with Matchers {
  "A Ludo TUI" should {

    val controller = new Controller()
    val tui = new TUI(controller)

    val EmptyCell = Cell("")

    "initially add players to the game when welcomed" in {
      controller.newMessage(WelcomeMessage)
      controller.message should be (WelcomeMessage)

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
    "with undo and redo to remove a player" in {
      tui.processInput("z")
      controller.players.size should be(1)

      tui.processInput("y")
      controller.players.size should be(2)

      tui.processInput("hans")
      controller.players.size should be(3)

      controller.players.foreach(p => p.name matches "alex|lukas|hans")
      println(controller.players)
    }
    "until start is pressed or 4 players are added" in {
      tui.processInput("start")
      controller.gameState.state should be(RollState(controller))
      controller.message should be(RollDiceMessage)
    }
    "switch between win conditions without changing the games current state" in {
      val same_state = controller.gameState.state

      tui.processInput("one")
      controller.winStrategy should be(OnePinWinStrategy())
      controller.gameState.state should be(same_state)

      tui.processInput("all")
      controller.winStrategy should be(AllPinWinStrategy())
      controller.gameState.state should be(same_state)
    }
    "throw a dice with any input" in {
      controller.pips should be (0)
      tui.processInput("any input for dice throw")
      controller.pips should be > 0
      if (controller.pips >= 6) {
        controller.message should be(ChoosePinMessage)
      } else {
        controller.message should be (RollDiceMessage)
      }


    }
    "don't move a selected pin from base when rolled a four without previous six" in {
      // simulate a thrown 4 ***** controller.rollDice() => 4
      controller.pips = 4
      controller.gameState.state = DrawState(controller)
      tui.processInput("1")

      controller.game should be(defined)
      controller.game.get.board.spots(controller.players(0).team.basePosition) should be(Cell("B1"))
      controller.game.get.board.spots(controller.players(0).team.startPosition) should be(EmptyCell)
    }
    "retry when there is an invalid pin input" in {
      val same_player = controller.currentPlayer
      val invalid_input = ""

      controller.pips = 6
      controller.gameState.state = DrawState(controller)
      controller.drawPin(4)

      tui.processInput(invalid_input)
      tui.processInput(invalid_input)

      controller.currentPlayer should be(same_player)
      controller.message should be(ChoosePinMessage)
    }
    "switch player and move a pin from base to start field when rolled a 6" in {
      controller.currentPlayer should be(defined)
      controller.currentPlayer.get should be(controller.players(1))

      // simulate a thrown 6 ***** controller.rollDice() => 6
      controller.gameState.state = DrawState(controller)
      controller.pips = 6
      controller.pips should be(6)

      tui.processInput("3")
      val base_pin3 = controller.players(1).team.basePosition + 2
      val id_pin3 = "R3"

      controller.game should be (defined)
      controller.game.get.board.spots(base_pin3) should be(EmptyCell)
      controller.game.get.board.spots(controller.players(1).team.startPosition) should be(Cell(id_pin3))
    }
    "save and load the game as implemented in controller" in {
      val prev_controller = controller
      tui.processInput("s") should be (prev_controller.save())
      val next_controller = controller
      tui.processInput("l") should be (next_controller.save())
    }
  }
}
