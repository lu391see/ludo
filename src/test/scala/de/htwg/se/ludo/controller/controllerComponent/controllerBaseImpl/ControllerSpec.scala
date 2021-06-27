package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{DrawState, RollState, SetupState}
import de.htwg.se.ludo.model.diceComponent.DiceInterface
import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}
import de.htwg.se.ludo.util.{ChoosePinMessage, EnterPlayerNameMessage, GameBoardUninitializedMessage, NextPlayerMessage, NoCurrentPlayerMessage, PinIsAlreadyFinishedMessage, PlayerRolledDiceMessage, PlayerWonGameMessage}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class DiceMock(nonrandom: Int) extends DiceInterface {
  def throwing: Int = nonrandom

  override val pips: Int = nonrandom
}

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
        controller.currentPlayer = Some(controller.players(0))
        controller.pips = 6
        controller.drawPin(2)
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.startPosition)
      }
      "undo should then revert this move" in {
        controller.currentPlayer = Some(controller.players(0))
        controller.undo()
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.basePosition + 1)
      }
      "and a redo should revert the undo" in {
        controller.currentPlayer = Some(controller.players(0))
        controller.redo()
        controller.game.get.findPinPosition(controller.currentPlayer.get, 2) should be (controller.currentPlayer.get.team.startPosition)
      }

      /*"stay in RollState when player has no pins on the field and no 6 is rolled" in {
        controller.switchPlayer()
        controller.currentPlayer.get should be(controller.players(1))
        controller.gameState.state = RollState(controller)

        controller.handleInput("")
        // fixme: this needs to be != 6 otherwise the test will fail

        controller.currentPlayer.get should be(controller.players(2))
        controller.gameState.state should be(RollState(controller))
      }*/
      "get from Roll to DrawState as soon as the first pin is on the field" in {
        controller.pips = 6
        controller.drawPin(1)
        controller.gameState.state = RollState(controller)
        controller.handleInput("")
        controller.gameState.state = DrawState(controller)
      }
      "stay in DrawState when input is invalid" in {
        controller.handleInput("abc")
        controller.gameState.state should be (DrawState(controller))
        controller.message should be (ChoosePinMessage)
      }
      "stay in DrawState when input is out of range" in {
        controller.handleInput("5")
        controller.gameState.state should be (DrawState(controller))
        controller.message should be (ChoosePinMessage)
      }
      "stay in DrawState when chosen pin is finished" in {
        controller.switchPlayer()
        controller.setWinStrategy(AllPinWinStrategy())
        controller.pips = 6
        controller.drawPin(4)
        controller.pips = 40
        controller.drawPin(4)
        controller.gameState.state = DrawState(controller)
        controller.handleInput("4")
        controller.gameState.state should be (DrawState(controller))
        controller.message should be (PinIsAlreadyFinishedMessage(4))
      }
      "stay in DrawState when chosen pin is not drawable" in {
        // todo: test controller.canNotDrawWithThisPin() usage in DrawState
      }
      "move to next Players RollState when input got processed successfully" in {
        controller.currentPlayer = Some(controller.players(0))
        controller.gameState.state should be (DrawState(controller))
        controller.pips = 4
        controller.handleInput("2")
        controller.gameState.state should be (RollState(controller))
        controller.currentPlayer.get should be (controller.players(1))
      }
      "when set to AllPinWinStrategy should be finished" in {
        /*controller.setWinStrategy(OnePinWinStrategy())
        controller.handleInput("")
        controller.pips = 6
        controller.drawPin(4)
        controller.pips = 40
        println("testing2" + controller.game.get.toString)
        controller.gameState.state = DrawState(controller)
        controller.isWon should be (true)
        controller.handleInput("4")
        controller.message should be (PlayerWonGameMessage(controller.currentPlayer.get))*/
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
