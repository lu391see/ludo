package de.htwg.se.ludo.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.playerComponent.Player

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}
import de.htwg.se.ludo.model.playerComponent.Player

class FileIO extends FileIOInterface {
  override def load(): (GameInterface, Int) = ???

  def gameToJson(currentPlayer: Player, players: Vector[Player], game: GameInterface) = {
    Json.obj(
      "currentPlayerIndex" -> JsNumber(players.lastIndexOf(currentPlayer)),
      "players" -> Json.toJson(
        for (player <- game.players)
          yield Json.obj(
            "name" -> player.name,
            "color" -> player.team.toColorString.toString,
            "basePosition"-> JsNumber(player.team.basePosition),
            "startPosition" -> JsNumber(player.team.startPosition),
            "homePosition" -> JsNumber(player.team.homePosition),
          )
      ),
      "board" -> Json.toJson(
        for (index <- 0 to game.board.spots.size)
          yield {
            var obj = Json.obj(
              "id" -> JsNumber(index),
            )
            if (game.board.spots(index).isSet) {
              obj = obj.++(Json.obj(
                "value" -> game.board.spots(index).value
              ))
            }
            obj
          }
      )
    )
  }


  override def save(currentPlayer: Player, players: Vector[Player], game: GameInterface): Unit = {
    import java.io._

    val pw = new PrintWriter(new File("ludo.json"))
    pw.write(Json.prettyPrint(gameToJson(currentPlayer, players, game)))
    pw.close()
  }
}
