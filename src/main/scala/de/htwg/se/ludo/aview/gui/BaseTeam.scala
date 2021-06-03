package de.htwg.se.ludo.aview.gui
import scala.swing._

case class BaseTeam(team: String, color: Color, basePos: Int) extends GridPanel(2,2) {
  //TODO: Check for Fields (solved with reactions)
  for (i <- 1 to 4) {
    contents += Pin(team + i.toString, color, basePos + i - 1)
  }
}
