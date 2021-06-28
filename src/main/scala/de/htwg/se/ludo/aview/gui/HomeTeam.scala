package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}
import de.htwg.se.ludo.model.gameComponent.BoardInterface

import scala.collection.mutable
import scala.swing.{Color, Component, GridPanel}

case class HomeTeam(
    color: Color,
    homePos: Int,
    controller: ControllerInterface
) extends GridPanel(2, 2) {
  for (i <- 1 to 4) {
    contents += new Field(homePos + i - 1)
  }
  listenTo(controller)

  reactions += {
    case event: PinDrawn =>
      val changes = findChanges(event.oldBoard, event.newBoard)
      if (changes.nonEmpty && changes.head._2.nonEmpty) {
        val to = changes.head._2
        val to_field_idx = getFieldIndex(to.get)
        val pin = event.newBoard.spots(to.get)
        val newContents =
          contents
            .clone()
            .updated(
              to_field_idx,
              Pin(pin.pinNumber, pin.colorFromChar, to.get, controller)
            )

        draw(newContents)
      }

  }

  def findChanges(
      oldBoard: BoardInterface,
      newBoard: BoardInterface
  ): Vector[(Int, Option[Int])] = {
    oldBoard.spots.zipWithIndex
      .filter(spot => spot._2 >= newBoard.baseSize && spot._1.isSet)
      .map(spot => spot._1)
      .map(pin =>
        (
          oldBoard.spots.indexOf(pin),
          newBoard.spots.zipWithIndex
            .filter(spot => spot._2 >= newBoard.gameSize && spot._1.isSet)
            .map(spot => spot._1)
            .find(spot => spot.value == pin.value)
            .map(spot =>
              newBoard.spots.indexWhere(sp => {
                sp.value == spot.value
              })
            )
        )
      )
  }

  def draw(newContents: mutable.Buffer[Component]): Unit = {
    contents.clear()
    contents ++= newContents
    repaint
  }

  def getFieldIndex(pinPosition: Int): Int = {
    contents.indexWhere(c => {
      c.name.toInt.equals(pinPosition)
    })
  }
}
