package de.htwg.se.ludo.model

sealed abstract class Message(message: String) {
  def print(): Unit = {
    println(message)
  }

  override def toString: String = {
    message
  }
}

case object ChoosePinMessage extends Message("Please choose a pin [1-4].")
case object GameBoardUninitializedMessage extends Message("There is no game board yet.")
case object RollDiceMessage extends Message("Press any key to roll a dice.")
case object NoCurrentPlayerMessage extends Message("There is no current player selected.")
case object InvalidCurrentPlayerAtSetupMessage extends Message("There should be no current player selected at the setup.")

case class PinIsAlreadyFinishedMessage(pin: Int) extends Message("The " + pin + " pin is already in its home row. Please choose a different pin.")
case class FirstPlayerMessage(player: Player) extends Message(player + " begins.")
case class NextPlayerMessage(player: Player) extends Message(player + " is next.")
case class PlayerRolledDiceMessage(player: Player, pips: Int) extends Message(player + " rolled " + pips + ".")