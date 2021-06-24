package de.htwg.se.ludo.controller

import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.gameStates.GameState
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.Game
import de.htwg.se.ludo.model.playerComponent.Player


case class SaveGame(
    game: Option[Game],
    players: Vector[Player],
    currentPlayer: Option[Player],
    gameState: GameState,
    pips: Int
)
