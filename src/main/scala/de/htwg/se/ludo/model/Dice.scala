package de.htwg.se.ludo.model
import scala.util.Random


class Dice(t1: Int) {
  def throwDice(): Int = t1
}

case class RandomDice() extends Dice(Random.nextInt(6) + 1) {
}