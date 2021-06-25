package de.htwg.se.ludo.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.playerComponent.Player

import play.api.libs.json.{JsNumber, JsValue, Json, Writes}
import de.htwg.se.ludo.model.playerComponent.Player

import scala.io.Source

class FileIO extends FileIOInterface {
  override def load(): (GameInterface, Int) = {
    val source: String = Source.fromFile("ludo.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    val currentPlayerIndex = (json \ "currentPlayerIndex").get.toString().toInt
    val player_amount = (json \"amount").get.toString().toInt
//    for (index <- 0 until )
//    (Game(), currentPlayerIndex)
    (null, 0)
  }

  def gameToJson(currentPlayer: Player, players: Vector[Player], game: GameInterface) = {
    Json.obj(
      "currentPlayerIndex" -> JsNumber(players.lastIndexOf(currentPlayer)),
      "amount" -> JsNumber(players.size),
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
        for (index <- 0 until game.board.spots.size)
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


  override def save(currentPlayer: Player, game: GameInterface): Unit = {
    import java.io._

    val pw = new PrintWriter(new File("ludo.json"))
    pw.write(Json.prettyPrint(gameToJson(currentPlayer, game.players, game)))
    pw.close()
  }
}
