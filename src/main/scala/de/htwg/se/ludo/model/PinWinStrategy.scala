package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.WinStrategy

case class OnePinWinStrategy() extends WinStrategy {
  override def hasWon(player: Player): Boolean = {
    player.team.pins.exists(pin =>
      player.team.isFinished(player.team.pins.indexOf(pin))
    )
  }
}

case class AllPinWinStrategy() extends WinStrategy {
  override def hasWon(player: Player): Boolean = {
    player.team.pins.forall(pin =>
      player.team.isFinished(player.team.pins.indexOf(pin))
    )
  }
}