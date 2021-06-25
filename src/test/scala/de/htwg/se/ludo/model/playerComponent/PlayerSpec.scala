package de.htwg.se.ludo.model.playerComponent

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.awt.Color

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "set to Lukas" should {
      val player = Player("Lukas", new Team(Color.yellow, 0, 16, 56))
      "have a name" in {
        player.name should be("Lukas")
      }
      "have a nice String representation" in {
        player.toString should be("Player Y: 'Lukas'")
      }
      "not be able to move for now" in {
        player.sixRolled should be(false)
      }
    }
    "created by a builder" should {
      val builder = PlayerBuilder()
      builder.setPlayerName("Alex")
      builder.setPlayerTeam(new Team(Color.green, 4, 26, 66))
      val player2 = builder.build()
      "have a name" in {
        player2.name should be("Alex")
      }
      "have a Team and not be able to move for now" in {
        player2.toString should be("Player G: 'Alex'")
        player2.sixRolled should be(false)
      }
    }
    "created with an invalid Color" should {
      "have no representation in Strings" in {
        val player3 = Player("greyPlayer", new Team(Color.gray, 0, 0, 0))
        player3.toString should be("Player -: 'greyPlayer'")
      }
    }
  }
}