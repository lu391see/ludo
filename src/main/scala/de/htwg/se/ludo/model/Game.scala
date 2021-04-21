package de.htwg.se.ludo.model

case class Game(field: Field[Cell], players: Vector[Player]) {

  def draw_pin(player: Player, pin: Int, dice_roll: Int): Game = {
    var game: Game = this
    if(player.playerPins(pin).position + dice_roll < player.defaultPosition + 40) {
      game = Game(field.replaceCell(player.playerPins(pin).position, Cell(0)), players)
      player.playerPins(pin).addPosition(dice_roll)
      game = Game(game.field.replaceCell(player.playerPins(pin).position, Cell(player.playerPins(pin).index)), players)
    }
    else {
      player.hasWon = true
      game = Game(field.replaceCell(player.playerPins(pin).position, Cell(0)), players)
    }
    game
  }
}
