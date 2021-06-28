package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}
import de.htwg.se.ludo.model.gameComponent.{BoardInterface}

import scala.collection.mutable
import scala.swing.{BoxPanel, Color, Component, Orientation}

case class FieldRow(
    orientation: Orientation.Value,
    controller: ControllerInterface,
    startFieldPos: Int,
    startFieldColor: Color
) extends BoxPanel(orientation = orientation) {
  val startField = new StartField(color = startFieldColor, pos = startFieldPos)

  listenTo(controller)

  reactions += {
    case event: PinDrawn =>
      val changes = findChanges(event.oldBoard, event.newBoard)
      var newContents = contents.clone()
      changes.foreach(change => {
        val from = change._1
        val to = change._2

        if (from >= event.oldBoard.baseSize && getFieldIndex(from) != -1) {
          val field_idx = getFieldIndex(from)
          newContents = newContents.updated(field_idx, getClearField(from))
        }

        if (to.nonEmpty && getFieldIndex(to.get) != -1) {
          val field_idx = getFieldIndex(to.get)
          val pin = event.newBoard.spots(to.get)
          newContents = newContents.updated(
            field_idx,
            Pin(pin.pinNumber, pin.colorFromChar, to.get, controller)
          )
        }
      })
      draw(newContents)
  }

  def draw(newContents: mutable.Buffer[Component]): Unit = {
    contents.clear()
    contents ++= newContents
    repaint
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
            .filter(spot =>
              spot._2 >= newBoard.baseSize && spot._2 < newBoard.gameSize
            )
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

  def getClearField(pinPosition: Int): Field = {
    if (pinPosition == startFieldPos) startField else new Field(pinPosition)
  }

  def getFieldIndex(pinPosition: Int): Int = {
    contents.indexWhere(c => {
      c.name.toInt.equals(pinPosition)
    })
  }
}
