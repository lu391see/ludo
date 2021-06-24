package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface

import scala.swing._
import java.awt.Color

case class Board(controller: ControllerInterface)
    extends BoxPanel(orientation = Orientation.Vertical) {
  private val teams = controller.teams
  contents += new BorderPanel {
    add(
      BaseTeam(teams(0).color, teams(0).basePosition, controller),
      BorderPanel.Position.West
    )
    add(
      BaseTeam(teams(1).color, teams(1).basePosition, controller),
      BorderPanel.Position.East
    )
  }
  contents += Swing.VStrut(20)
  contents += new BorderPanel {
    add(
      new HomeTeam(Color.lightGray, teams(0).homePosition, controller),
      BorderPanel.Position.West
    )
    add(
      new HomeTeam(Color.lightGray, teams(1).homePosition, controller),
      BorderPanel.Position.East
    )
  }
  contents += HorizontalFieldRow(
    teams(0).color,
    teams(1).color,
    teams(0).startPosition,
    teams(1).startPosition,
    false,
    controller
  )
  contents += new BorderPanel {
    add(
      VerticalFieldRow(
        teams(3).startPosition,
        teams(0).homePosition,
        true,
        controller,
      ),
      BorderPanel.Position.West
    )
    add(
      VerticalFieldRow(
        teams(1).startPosition,
        teams(2).startPosition,
        false,
        controller
      ),
      BorderPanel.Position.East
    )

  }
  contents += HorizontalFieldRow(
    teams(2).color,
    teams(3).color,
    teams(2).startPosition,
    teams(3).startPosition,
    true,
    controller
  )

  contents += new BorderPanel {
    add(
      new HomeTeam(Color.lightGray, teams(2).homePosition, controller),
      BorderPanel.Position.East
    )
    add(
      new HomeTeam(Color.lightGray, teams(3).homePosition, controller),
      BorderPanel.Position.West
    )
  }
  contents += Swing.VStrut(20)
  contents += new BorderPanel {
    add(
      BaseTeam(teams(2).color, teams(2).basePosition, controller),
      BorderPanel.Position.West
    )
    add(
      BaseTeam(teams(3).color, teams(3).basePosition, controller),
      BorderPanel.Position.East
    )
  }
}
