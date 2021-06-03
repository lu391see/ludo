package de.htwg.se.ludo.aview.gui
import scala.swing._
import java.awt.Color

case class Board() extends BoxPanel(orientation = Orientation.Vertical) {
  contents += new BorderPanel {
    add(BaseTeam("A", Color.black, 0), BorderPanel.Position.West)
    add(BaseTeam("B", Color.red, 4), BorderPanel.Position.East)
  }
  contents += new BorderPanel {
    add(new HomeTeam("A", Color.lightGray, 47), BorderPanel.Position.West)
    add(new HomeTeam("B", Color.lightGray, 51), BorderPanel.Position.East)
  }
  contents += HorizontalFieldRow(Color.black, Color.red, 16, 26)
  contents += new BorderPanel {
    add(VerticalFieldRow(35, 45), BorderPanel.Position.East)
    add(VerticalFieldRow(27, 35), BorderPanel.Position.West)
  }
  contents += HorizontalFieldRow(Color.green, Color.yellow, 36, 46)
  contents += new BorderPanel {
    add(new HomeTeam("C", Color.lightGray, 8), BorderPanel.Position.East)
    add(new HomeTeam("D", Color.lightGray, 12), BorderPanel.Position.West)
  }
  contents += new BorderPanel {
    add(BaseTeam("C", Color.yellow, 55), BorderPanel.Position.East)
    add(BaseTeam("D", Color.green, 59), BorderPanel.Position.West)
  }
}
