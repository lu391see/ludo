package de.htwg.se.ludo.aview.gui
import java.awt.Color
import javax.swing.border.LineBorder
import scala.swing._

class Field(pos: Int) extends Panel () {
  name = pos.toString
  background = Color.lightGray
  preferredSize = new Dimension(75, 75)
  opaque = true
  border = Swing.BeveledBorder(Swing.Raised)
}
