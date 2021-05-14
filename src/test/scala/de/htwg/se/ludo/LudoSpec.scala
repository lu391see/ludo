package de.htwg.se.ludo

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

import java.io.ByteArrayInputStream

class LudoSpec extends AnyWordSpec with Matchers {
  "A Game" when {
    "started with 2 players" should {
      "run  without exceptions" in {
        val in = new ByteArrayInputStream("Player_name1\nplayer_name2\nstart\n1\n2\n2\n1\nq\n".getBytes)
        Console.withIn(in) {
          noException should be thrownBy Ludo.main(Array())
        }
      }
    }
  }
}
