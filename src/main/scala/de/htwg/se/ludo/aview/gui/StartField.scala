package de.htwg.se.ludo.aview.gui

import java.awt.Color

class StartField(color: Color, pos:Int) extends Field(pos) {
    background = color
    if (color.equals(Color.black)) {
        for(_ <- 1 until 12) {
          background = background.brighter()
        }
    } else {
        for(_ <- 1 until 2) {
            background = background.darker()
        }
    }

}
