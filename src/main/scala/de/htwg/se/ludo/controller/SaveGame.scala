package de.htwg.se.ludo.controller

import de.htwg.se.ludo.model.{Game, Player}

case class SaveGame(
    game: Option[Game],
    players: Vector[Player],
    currentPlayer: Option[Player],
    gameState: GameState,
    pips: Int
)
