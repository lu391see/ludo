package de.htwg.se.ludo.model.diceComponent.dice8Impl

import de.htwg.se.ludo.model.diceComponent.DiceInterface

import scala.util.Random


class Dice extends DiceInterface {
  val pips: Int = 8
  def throwing: Int = Random.nextInt(pips) + 1
}