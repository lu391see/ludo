package de.htwg.se.ludo.model.fileIoComponent.fileIoXmlImpl

import de.htwg.se.ludo.model.fileIoComponent.FileIOInterface
import de.htwg.se.ludo.model.gameComponent.{BoardInterface, CellInterface, GameInterface}
import de.htwg.se.ludo.model.playerComponent.Player

import scala.xml.{Elem, PrettyPrinter}

class FileIO extends FileIOInterface {
  override def load(): GameInterface = ???

  def playersToXml(currentPlayerIndex: Int, players: Vector[Player]) = {
    <players currentPlayerIndex={ currentPlayerIndex.toString }>
      {
      for (player <- players){
        <player name={ player.name }>
        <team color={ player.team.toColorString }
              basePosition={ player.team.basePosition.toString }
              startPosition={ player.team.startPosition.toString }
              homePosition={ player.team.homePosition.toString }>
        </team>
        </player>

        }
      }
    </players>
  }

  def saveString(currentPlayer: Player, players: Vector[Player]): Unit = {


  }

  def boardToXml(board: BoardInterface): Elem = {
    <board>
      {
      for{
        spot <- board.spots
      }
      yield spotToXml(spot)
      }
    </board>
  }

  def spotToXml(spot: CellInterface): Unit = {
    if(spot.isSet) {
      <spot value={spot.value}>
      </spot>
    }
  }

  override def save(currentPlayer: Player, players: Vector[Player], game: GameInterface): Unit = {
    import java.io._
    val pw = new PrintWriter(new File("ludo.xml"))
    val prettyPrinter = new PrettyPrinter(120, 4)
    val currentPlayerIndex = players.lastIndexOf(currentPlayer)

    val player_xml = playersToXml(currentPlayerIndex, players)
    val xml = prettyPrinter.format(player_xml)
    pw.write(xml)
    pw.close()
  }
}
