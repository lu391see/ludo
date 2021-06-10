package de.htwg.se.ludo.controller

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.boardComponent.boardBaseImpl.{Board, Cell}
import de.htwg.se.ludo.model.playerComponent.{Player, Team}
import de.htwg.se.ludo.model.{Game, playerComponent}
import de.htwg.se.ludo.util.Observer

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val players: Vector[Player] = Vector[Player](Player("p2", new Team(Color.yellow, 0, 15, 55)))
      //val game = new Game(Board(40, new Cell(" ")), players)
      val controller = new Controller()
      "notify its Observer after setting a cell" in {
        //controller.draw(players(0),0,4)
        //observer.updated should be(true)
        //controller.game.field.cell(4).value should be (11)
      }
      "notify its Observer after creation" in {
        //controller.createStartGame(4, players)
        //observer.updated should be(true)
        //controller.game.field.size should be(4)
      }

    }
  }

}
