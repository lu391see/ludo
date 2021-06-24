package de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.{DrawState, RollState, SetupState}
import de.htwg.se.ludo.util.{EnterPlayerNameMessage, NoCurrentPlayerMessage}
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "being a Publisher" should {
      // val players: Vector[Player] = Vector[Player](Player("p2", new Team(Color.yellow, 0, 15, 55)))
      //val game = new Game(Board(40, new Cell(" ")), players)
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

        //controller.draw(players(0),0,4)
        //observer.updated should be(true)
        //controller.game.field.cell(4).value should be (11)
        //controller.createStartGame(4, players)
        //observer.updated should be(true)
        //controller.game.field.size should be(4)
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
        controller.currentPlayer = Some(controller.players(0))
        controller.gameState.state = RollState(controller)
        controller.handleInput("...")
        controller.gameState.state should be (DrawState(controller))
      }
      "beating an enemy pin when ahead" in {
      }


    }
  }

}
