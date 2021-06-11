package de.htwg.se.ludo.model.diceComponent.dice8Impl

import de.htwg.se.ludo.model.diceComponent.DiceInterface

import scala.util.Random


case class Dice(pips: Int = Random.nextInt(8) + 1) extends DiceInterface