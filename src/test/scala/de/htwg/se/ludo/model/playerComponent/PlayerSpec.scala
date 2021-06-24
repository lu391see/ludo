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
        player.toString should be("Player Yellow: 'Lukas'")
      }
      "not be able to move for now" in {
        player.sixRolled should be(false)
      }
      "not set to Winner yet" in {
        player.hasWon should be(false)
      }
      "have 4 pins with Color-identifier in front of pin index" in {
        player.team.pinID(0) should be("Y1")
        player.team.position(0) should be(player.team.basePosition)
        player.team.pinID(1) should be("Y2")
        player.team.position(1) should be(player.team.basePosition + 1)
        player.team.pinID(3) should be("Y4")
      }
      "spawn pins at home location when finished" in {
        player.finish(0)
        player.team.position(0) should be (player.team.homePosition)
      }
    }
    "created by a builder" should {

      "have a name and Team" in {
        val builder = PlayerBuilder()
        builder.setPlayerName("Alex")
        builder.setPlayerTeam(new Team(Color.green, 4, 26, 66))
        val player2 = builder.build()
        player2.name should be ("Alex")
        player2.toString should be("Player Green: 'Alex'")
        player2.team.pinID(0) should be("G1")
        player2.team.position(0) should be(player2.team.basePosition)
        player2.team.pinID(1) should be("G2")
        player2.team.position(1) should be(player2.team.basePosition + 1)
        player2.team.pinID(3) should be("G4")

      }
    }
    "created with an invalid Color" should {
      "have no representation in Strings" in {
        val player3 = Player("greyPlayer", new Team(Color.gray, 0, 0, 0))
        player3.toString should be("Player : 'greyPlayer'")
      }
    }
  }
}