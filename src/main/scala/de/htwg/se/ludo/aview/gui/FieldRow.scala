package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}
import de.htwg.se.ludo.model.gameComponent.{BoardInterface, CellInterface}

import java.awt.Color
import scala.swing.{BoxPanel, Color, Orientation}

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
      val changedPinPositions =
        findChangedPinPositions(event.oldBoard, event.newBoard)
      println("changedPinPositions", changedPinPositions.toString)
      val oldPos = changedPinPositions._1
      val newPos = changedPinPositions._2

      println("oldPos", getFieldIndex(oldPos))
      println("newPos", getFieldIndex(newPos))
      if (getFieldIndex(oldPos) != -1 || getFieldIndex(newPos) != -1) {
        var newContents = contents
        if (getFieldIndex(oldPos) != -1) {
          val oldField =
            if (oldPos == startFieldPos) startField
            else new Field(oldPos)
          newContents = newContents.updated(getFieldIndex(oldPos), oldField)
        }
        if (getFieldIndex(newPos) != -1) {
          val pinNumber = event.newBoard.spots(newPos).pinNumber
          val pinColor = event.newBoard.spots(newPos).color
          val newField = Pin(pinNumber, toColor(pinColor), newPos, controller)
          newContents = newContents.updated(getFieldIndex(newPos), newField)
        }
        newContents.foreach(content => println(content.name, content.visible))
        contents.clear()
        contents ++= newContents
        contents.foreach(content => println(content.name, content.visible))

        repaint
      }

  }

  def findChangedPinPositions(
      oldBoard: BoardInterface,
      newBoard: BoardInterface
  ): (Int, Int) = {
    var changedPinPositions: Vector[Int] = Vector.empty
    for (i <- 0 until oldBoard.size) {
      if (oldBoard.spots(i).value != newBoard.spots(i).value)
        changedPinPositions = changedPinPositions.appended(i)
    }

    if (
      (changedPinPositions(1) - changedPinPositions(
        0
      )).abs > controller.getDice.pips && changedPinPositions(
        1
      ) < newBoard.gameSize
    ) {
      return Tuple2(changedPinPositions(1), changedPinPositions(0))
    }
    Tuple2(changedPinPositions(0), changedPinPositions(1))
  }

  def toColor(color: Char): Color = {
    color match {
      case 'B' => Color.black
      case 'R' => Color.red
      case 'Y' => Color.yellow
      case 'G' => Color.green
      case _   => Color.white
    }
  }

  def getFieldIndex(pinPosition: Int): Int = {
    contents.indexWhere(c => c.name.toInt.equals(pinPosition))
  }
}
