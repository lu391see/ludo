package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{DrawState, RollState, SetupState}
import de.htwg.se.ludo.util.{EnterPlayerNameMessage, NoCurrentPlayerMessage}
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
      "show error message if current player is missing while rolling a Dice" in {
        controller.currentPlayer = None
        controller.rollDice()
        controller.message should be (NoCurrentPlayerMessage)
      }
      "and return the message as string (to be used from the GUI textfield)" in {
        controller.messageToString() should be (NoCurrentPlayerMessage.toString)
      }
      "after rolling a dice get into DrawState" in {
        /* fixme: next state is not predictable. Set one pin of the current player on the field first
        controller.currentPlayer = Some(controller.players(0))
        controller.gameState.state = RollState(controller)
        controller.handleInput("...")
        controller.gameState.state should be (DrawState(controller))*/
      }
      "beating an enemy pin when ahead" in {
      }


    }
  }

}
