package de.htwg.se.ludo.model.fileIoComponent.fileIoJsonImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.{Board, Cell, Game}
import de.htwg.se.ludo.model.gameComponent.{BoardInterface, GameInterface}
import de.htwg.se.ludo.model.playerComponent.{Player, Team}
import de.htwg.se.ludo.model.playerComponent.Player
import play.api.libs.json.{JsLookupResult, JsNumber, JsObject, JsValue, Json}

import java.awt.Color
import scala.io.Source

class FileIO extends FileIOInterface {
  override def load(): (GameInterface, Int) = {
    val source: String = Source.fromFile("ludo.json").getLines().mkString
    val json: JsValue = Json.parse(source)
    val currentPlayerIndex = (json \ "currentPlayerIndex").get.toString().toInt
    val playerAmount = (json \ "playerAmount").get.toString().toInt
    val spots = json \\ "value"
    val players = json \ "players"
    val baseSize = 16

    (
      Game(
        boardFromJson(spots, baseSize),
        playersFromJson(players, playerAmount)
      ),
      currentPlayerIndex
    )
  }

  def gameToJson(
      currentPlayer: Player,
      players: Vector[Player],
      game: GameInterface
  ) = {
    Json.obj(
      "currentPlayerIndex" -> JsNumber(players.lastIndexOf(currentPlayer)),
      "playerAmount" -> JsNumber(players.size),
      "players" -> Json.toJson(
        for (player <- game.players)
          yield Json.obj(
            "name" -> player.name,
            "color" -> player.team.toColorString.toString,
            "basePosition" -> JsNumber(player.team.basePosition),
            "startPosition" -> JsNumber(player.team.startPosition),
            "homePosition" -> JsNumber(player.team.homePosition)
          )
      ),
      "board" -> Json.toJson(
        for (index <- game.board.spots.indices)
          yield {
            Json.obj(
              "value" -> game.board.spots(index).value
            )
          }
      )
    )
  }

  def boardFromJson(
      spots: scala.collection.Seq[JsValue],
      baseSize: Int
  ): BoardInterface = {
    Board(spots.map(spot => Cell(spot.as[String])).toVector, baseSize)
  }

  def playersFromJson(
      players: JsLookupResult,
      playerAmount: Int
  ): Vector[Player] = {

    (0 until playerAmount)
      .map(i =>
        Player(
          (players(i) \ "name").as[String],
          new Team(
            fromColorString((players(i) \ "color").as[String].charAt(0)),
            (players(i) \ "basePosition").as[Int],
            (players(i) \ "startPosition").as[Int],
            (players(i) \ "homePosition").as[Int]
          )
        )
      )
      .toVector
  }

  def fromColorString(color: Char): Color = {
    color match {
      case 'B' => Color.black
      case 'R' => Color.red
      case 'Y' => Color.yellow
      case 'G' => Color.green
      case _   => Color.white
    }
  }

  override def save(currentPlayer: Player, game: GameInterface): Unit = {
    import java.io._

    val pw = new PrintWriter(new File("ludo.json"))
    pw.write(Json.prettyPrint(gameToJson(currentPlayer, game.players, game)))
    pw.close()
  }
}
