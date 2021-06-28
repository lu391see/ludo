package de.htwg.se.ludo.model

import de.htwg.se.ludo.model.gameComponent.BoardInterface
import de.htwg.se.ludo.model.playerComponent.Player
import de.htwg.se.ludo.util.WinStrategy

case class OnePinWinStrategy() extends WinStrategy {
  override def hasWon(player: Player, board: BoardInterface): Boolean = {
    player.team.pins.exists(pin =>
      board.spots.indexWhere(spot => {
        spot.isSet && spot.pinNumber == pin.number
      }) >= player.team.homePosition
    )
  }
}

case class AllPinWinStrategy() extends WinStrategy {
  override def hasWon(player: Player, board: BoardInterface): Boolean = {
    player.team.pins.forall(pin =>
      board.spots.indexWhere(spot => {
        spot.isSet && spot.pinNumber == pin.number
      }) >= player.team.homePosition
    )
  }
}
