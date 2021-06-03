package de.htwg.se.ludo.aview.gui
import java.awt.Color
import javax.swing.border.LineBorder
import scala.swing._

class Field(pos: Int) extends Panel () {
  background = Color.lightGray
  preferredSize = new Dimension(75, 75)
  opaque = true
  border = new LineBorder(Color.white, 2)
}
