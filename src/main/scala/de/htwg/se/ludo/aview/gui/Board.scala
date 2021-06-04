package de.htwg.se.ludo.aview.gui
import de.htwg.se.ludo.model.PlayerConstraints

import scala.swing._
import java.awt.Color

case class Board() extends BoxPanel(orientation = Orientation.Vertical) {
  private val teams = PlayerConstraints.teams
  contents += new BorderPanel {
    add(BaseTeam(teams(0).color, teams(0).basePosition), BorderPanel.Position.West)
    add(BaseTeam(teams(1).color, teams(1).basePosition), BorderPanel.Position.East)
  }
  contents += Swing.VStrut(20)
  contents += new BorderPanel {
    add(new HomeTeam(Color.lightGray, teams(0).homePosition), BorderPanel.Position.West)
    add(new HomeTeam(Color.lightGray, teams(1).homePosition), BorderPanel.Position.East)
  }
  contents += HorizontalFieldRow(teams(0).color, teams(1).color, teams(0).startPosition, teams(1).startPosition)
  contents += new BorderPanel {
    add(VerticalFieldRow(teams(3).startPosition + 1, teams(0).homePosition - 1), BorderPanel.Position.East)
    add(VerticalFieldRow(teams(1).startPosition + 1, teams(2).startPosition - 1), BorderPanel.Position.West)
  }
  // TODO: reverse
  contents += HorizontalFieldRow(teams(2).color, teams(3).color, teams(2).startPosition, teams(3).startPosition)

  contents += new BorderPanel {
    add(new HomeTeam(Color.lightGray, teams(2).homePosition), BorderPanel.Position.East)
    add(new HomeTeam(Color.lightGray, teams(3).homePosition), BorderPanel.Position.West)
  }
  contents += Swing.VStrut(20)
  contents += new BorderPanel {
    add(BaseTeam(teams(2).color, teams(2).basePosition), BorderPanel.Position.East)
    add(BaseTeam(teams(3).color, teams(3).basePosition), BorderPanel.Position.West)
  }
}
