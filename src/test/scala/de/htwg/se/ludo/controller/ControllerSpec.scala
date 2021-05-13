package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Cell, Field, Game, Player}
import de.htwg.se.ludo.util.Observer
import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class ControllerSpec extends AnyWordSpec with Matchers {
  "A Controller" when {
    "observed by an Observer" should {
      val players: Vector[Player] = Vector[Player](Player("", 1))
      val game = new Game(new Field(40, Cell(0)), players)
      val controller = new Controller(game)
      val observer = new Observer {
        var updated: Boolean = false
        def isUpdated: Boolean = updated
        override def update(): Boolean = {updated = true; updated}
      }
      controller.add(observer)
      "notify its Observer after setting a cell" in {
        controller.draw(players(0),0,4)
        observer.updated should be(true)
        controller.game.field.cell(4).value should be (11)
      }
      "notify its Observer after creation" in {
        controller.createStartGame(4, players)
        observer.updated should be(true)
        controller.game.field.size should be(4)
      }

    }
  }

}
