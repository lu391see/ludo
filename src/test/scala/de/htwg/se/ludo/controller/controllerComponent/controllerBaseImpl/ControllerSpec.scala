package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{DrawState, RollState, SetupState}
import de.htwg.se.ludo.util.{EnterPlayerNameMessage, GameBoardUninitializedMessage, NextPlayerMessage, NoCurrentPlayerMessage, PlayerRolledDiceMessage}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "being a Publisher" should {
      val controller = new Controller()
      "should start with the SetupState" in {
        controller.gameState.state should be (SetupState(controller))
      }
      "where it adds players via the SetupState" in {
        val empty_input = ""
        controller.handleInput(empty_input)
        controller.message should be (EnterPlayerNameMessage)

        controller.handleInput("lukas")
        controller.handleInput("alex")
        controller.players.size should be (2)
      }
      "and enters RollState once 4 players are added or 'start' is the input" in {
        controller.handleInput("hans")
        controller.handleInput("peter")
        controller.handleInput("start")
        controller.gameState.state should be (RollState(controller))
      }
      "show error messages if current player is missing while switching" in {
        controller.currentPlayer = None
        controller.switchPlayer()
        controller.message should be (NoCurrentPlayerMessage)
      }
      "and switch players in a loop" in {
        controller.currentPlayer = Some(controller.players(0))
        controller.switchPlayer()
        controller.message should be (NextPlayerMessage(controller.players(1)))
        controller.switchPlayer()
        controller.switchPlayer()
        controller.switchPlayer()
        controller.currentPlayer.get should be (controller.players(0))
      }
      "show error message if current player is missing while rolling a Dice" in {
        controller.currentPlayer = None
        controller.rollDice()
        controller.message should be (NoCurrentPlayerMessage)
      }
      "and return the message as string (to be used from the GUI textfield)" in {
        controller.messageToString() should be (NoCurrentPlayerMessage.toString)
      }
      "after rolling a dice show message when successful" in {
        controller.currentPlayer = Some(controller.players(0))
        controller.rollDice()
        controller.message should be (PlayerRolledDiceMessage(controller.players(0), controller.pips))
      }
      "use doStep when drawing a pin" in {
        controller.pips = 6
        controller.drawPin(2)
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.startPosition)
      }
      "undo should then revert this move" in {
        controller.undo()
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.basePosition + 1)
      }
      "and a redo should revert the undo" in {
        controller.redo()
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.startPosition)
      }



      "fail when trying to draw without a game initialized" in {
        //controller.game = None
        //controller.drawPin(2)
        //controller.message should be (GameBoardUninitializedMessage)
      }
      /* fixme: next state is not predictable. Set one pin of the current player on the field first
        controller.currentPlayer = Some(controller.players(0))
        controller.gameState.state = RollState(controller)
        controller.handleInput("...")
        controller.gameState.state should be (DrawState(controller))*/

    }
  }

}
