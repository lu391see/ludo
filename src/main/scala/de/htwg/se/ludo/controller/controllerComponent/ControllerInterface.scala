package de.htwg.se.ludo.controller.controllerComponent

import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.util.{Message, WinStrategy}

import scala.swing.Publisher

trait ControllerInterface extends Publisher {
  var game: Option[GameInterface]

  def handleInput(input: String): Unit

  def newGame(): Unit
  def addNewPlayer(name: String): Unit
  def switchPlayer(): Unit
  def rollDice(): Unit
  def drawPin(pin: Int): Unit

  def undo(): Unit
  def redo(): Unit

  def save(): Unit
  def load(): Unit

  def newMessage(message: Message): Unit
  def publishMessage(): Unit
  def messageToString(): String
  def setWinStrategy(winStrategy: WinStrategy): Unit
}
