package de.htwg.se.ludo.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.{Board, Cell, Game}
import de.htwg.se.ludo.model.gameComponent.{
  BoardInterface,
  CellInterface,
  GameInterface
}
import de.htwg.se.ludo.model.playerComponent.{Player, Team}

import java.awt.Color
import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOInterface {

  override def load(): (GameInterface, Int) = {
    val file = scala.xml.XML.loadFile("ludo.xml")
    val spotNodes = file \\ "spot"
    val playerNodes = file \\ "player"
    val playersNode: scala.xml.Node = (file \\ "players").head

    (
      Game(
        boardFromXml(spotNodes, 16),
        playersFromXml(playerNodes)
      ),
      currentPlayerIndexFromXml(playersNode)
    )
  }

  def playersToXml(currentPlayerIndex: Int, players: Vector[Player]): Elem = {
    <players currentPlayerIndex={currentPlayerIndex.toString}>
      {
      for (player <- players) yield {
        <player name={player.name}>
          <team color={player.team.toColorString.toString}
                basePosition={player.team.basePosition.toString}
                startPosition={player.team.startPosition.toString}
                homePosition={player.team.homePosition.toString}>
          </team>
        </player>

      }
    }
    </players>
  }

  def currentPlayerIndexFromXml(playersNode: scala.xml.Node): Int = {
    (playersNode \ "@currentPlayerIndex").text.toInt
  }

  def playersFromXml(playerNodes: scala.xml.NodeSeq): Vector[Player] = {
    playerNodes
      .map(playerNode => {
        val playerName = (playerNode \ "@name").text
        val color = (playerNode \\ "team" \ "@color").text.charAt(0)
        val basePosition = (playerNode \\ "team" \ "@basePosition").text.toInt
        val startPosition = (playerNode \\ "team" \ "@startPosition").text.toInt
        val homePosition = (playerNode \\ "team" \ "@homePosition").text.toInt

        Player(
          playerName,
          new Team(
            fromColorString(color),
            basePosition,
            startPosition,
            homePosition
          )
        )
      })
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
  def boardFromXml(
      spotNodes: scala.xml.NodeSeq,
      baseSize: Int
  ): Board = {
    Board(
      spotNodes.map(spotNode => Cell((spotNode \ "@value").text)).toVector,
      baseSize
    )
  }

  def boardToXml(board: BoardInterface): Elem = {
    <board>
      {
      for {
        spot <- board.spots
      } yield spotToXml(
        spot
      )
    }
    </board>
  }

  def spotToXml(spot: CellInterface): Elem = {
    <spot value={spot.value}>
    </spot>
  }

  def gameToXml(boardXml: Elem, playersXml: Elem): Elem = {
    <game>
      {boardXml}
      {playersXml}
    </game>
  }

  override def save(
      currentPlayer: Player,
      game: GameInterface
  ): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("ludo.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val players = game.players
    val currentPlayerIndex = players.lastIndexOf(currentPlayer)
    players.foreach(player => println(player.toString))
    val playersXml = playersToXml(currentPlayerIndex, players)
    val boardXml = boardToXml(game.board)
    val gameXml = gameToXml(boardXml, playersXml)
    val xml = prettyPrinter.format(gameXml)
    pw.write(xml)
    pw.close()
  }
}
