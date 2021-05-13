package de.htwg.se.ludo.model

import de.htwg.se.ludo.util.WinStrategy

case class AllPinWinStrategy() extends WinStrategy {
  override def hasWon(player: Player): Boolean = {
    player.team.pins.forall(pin =>
      player.team.isFinished(player.team.pins.indexOf(pin))
    )
  }
}
