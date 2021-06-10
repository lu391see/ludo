package de.htwg.se.ludo.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

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
        player.sixRolled should be (false)
      }
      "not set to Winner yet" in {
        player.hasWon should be(false)
      }
      "have 4 pins with number in front of index" in {
        player.team.color should be('Y')
        player.team.id(0) should be("Y1")
        player.team.position(0) should be(player.team.basePosition)
        player.team.id(1) should be("Y2")
        player.team.position(1) should be(player.team.basePosition + 1)
        player.team.id(3) should be("Y4")
      }
    }
  }


}