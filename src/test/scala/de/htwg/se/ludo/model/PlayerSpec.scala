package de.htwg.se.ludo.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers

class PlayerSpec extends AnyWordSpec with Matchers {
  "A Player" when {
    "set to Lukas" should {
      val player = Player("Lukas", new Team('Y', 0, 15, 55))
      "have a name" in {
        player.name should be("Lukas")
      }
      "have a nice String representation" in {
        player.toString should be("Player 1: Lukas")
      }
      "not set to Winner yet" in {
        player.hasWon should be(false)
      }
      "have 4 pins with number in front of index" in {
        player.team should be(1)
        player.team.id(0) should be("Y1")
        player.team.position(0) should be(0)
        player.team.id(0) should be("Y2")
        player.team.id(0) should be("Y4")
      }
    }
  }


}