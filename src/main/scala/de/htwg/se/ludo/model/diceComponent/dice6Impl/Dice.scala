package de.htwg.se.ludo.model.diceComponent.dice6Impl

import de.htwg.se.ludo.model.diceComponent.DiceInterface

import scala.util.Random


case class Dice(pips: Int = Random.nextInt(6) + 1) extends DiceInterface