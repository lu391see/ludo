package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}
import de.htwg.se.ludo.model.gameComponent.BoardInterface

import scala.collection.mutable
import scala.swing._

case class BaseTeam(color: Color, basePos: Int, controller: ControllerInterface)
    extends GridPanel(2, 2) {
  listenTo(controller)
  for (i <- 1 to 4) {
    contents += Pin(i, color, basePos + i - 1, controller)
  }

  reactions += {
    case event: PinDrawn =>
      val changes = findChanges(event.oldBoard, event.newBoard)
      var newContents = contents.clone()
      changes
        .filter(change => getFieldIndex(change._1) != -1)
        .foreach(change => {
          val from = change._1
          val to = change._2
          val from_field_idx = getFieldIndex(from)

          // spawning
          if (from < event.oldBoard.gameSize && from_field_idx != -1) {
            newContents =
              newContents.updated(from_field_idx, new StartField(color, from))
          }

          // basing
          if (
            to.nonEmpty && to.get < event.oldBoard.baseSize && getFieldIndex(
              to.get
            ) != -1
          ) {
            val pin = event.newBoard.spots(to.get)
            newContents = newContents.updated(
              getFieldIndex(to.get),
              Pin(pin.pinNumber, pin.colorFromChar, to.get, controller)
            )
          }

          draw(newContents)
        })

  }

  def findChanges(
      oldBoard: BoardInterface,
      newBoard: BoardInterface
  ): Vector[(Int, Option[Int])] = {
    oldBoard.spots.zipWithIndex
      .filter(spot => spot._2 < newBoard.gameSize && spot._1.isSet)
      .map(spot => spot._1)
      .map(pin =>
        (
          oldBoard.spots.indexOf(pin),
          newBoard.spots.zipWithIndex
            .filter(spot => spot._2 < newBoard.gameSize && spot._1.isSet)
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
