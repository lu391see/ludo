package de.htwg.se.ludo.model

case class Game(field: Field[Cell], players: Vector[Player]) {

  def draw_pin(player: Player, pin: Int, dice_roll: Int): Game = {
    if(player.playerPins(pin).position + dice_roll < player.defaultPosition + 40) {
      val removed_game = Game(field.replaceCell(player.playerPins(pin).position, Cell(0)), players)
      player.playerPins(pin).addPosition(dice_roll)
      Game(removed_game.field.replaceCell(player.playerPins(pin).position, Cell(player.playerPins(pin).index)), players)
    }
    else {
      player.hasWon = true
      Game(field.replaceCell(player.playerPins(pin).position, Cell(0)), players)
    }
  }
}
