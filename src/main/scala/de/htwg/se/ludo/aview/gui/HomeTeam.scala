package de.htwg.se.ludo.aview.gui

import scala.swing.Color

class HomeTeam(
    override val team: String,
    override val color: Color,
    override val basePos: Int
) extends BaseTeam(team, color, basePos) {}
