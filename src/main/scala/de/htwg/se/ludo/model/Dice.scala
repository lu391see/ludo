package de.htwg.se.ludo.model
import scala.util.Random


class Dice(val pips: Int)

case class RandomDice() extends Dice(Random.nextInt(6) + 1)