package de.htwg.se.ludo.controller

import de.htwg.se.ludo.util.State

case class GameState(controller: Controller) {
  var state: State[GameState] = SetupState(controller)

  def handle(string: String): Unit = {
    state.handle(string, this)
  }

  def nextState(state: State[GameState]): Unit = {
    this.state = state
  }
}
