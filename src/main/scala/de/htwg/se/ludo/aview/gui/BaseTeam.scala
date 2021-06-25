package de.htwg.se.ludo.aview.gui

import de.htwg.se.ludo.controller.controllerComponent.{
  ControllerInterface,
  PinDrawn
}
import de.htwg.se.ludo.model.gameComponent.BoardInterface

import scala.swing._

case class BaseTeam(color: Color, basePos: Int, controller: ControllerInterface)
    extends GridPanel(2, 2) {
  listenTo(controller)
  for (i <- 1 to 4) {
    contents += Pin(i, color, basePos + i - 1, controller)
  }
  reactions += {
    case event: PinDrawn =>
      val changedPinPositions =
        findChangedPinPositions(event.oldBoard, event.newBoard)
      val oldPos = changedPinPositions._1
      val newPos = changedPinPositions._2

      if (isSpawned(oldPos)) {
        val newContents =
          contents.updated(getFieldIndex(oldPos), new StartField(color, oldPos))
        contents.clear()
        contents ++= newContents
      } else if (isBased(newPos)) {
        val pinNumber = event.newBoard.spots(newPos).pinNumber
        val newContents = contents.updated(
          getFieldIndex(newPos),
          Pin(pinNumber, color, newPos, controller)
        )
        contents.clear()
        contents ++= newContents
      }

      repaint
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
    /*
    if(changedPinPositions(0) < changedPinPositions(1)) {
      return Tuple2(changedPinPositions(1), changedPinPositions(1))
    }
     */
    Tuple2(changedPinPositions(0), changedPinPositions(1))
  }

  def getFieldIndex(pinPosition: Int): Int = {
    contents.indexWhere(c => c.name.toInt.equals(pinPosition))
  }

  def isSpawned(oldPos: Int): Boolean = {
    oldPos >= basePos + 4 && getFieldIndex(oldPos) != -1
  }

  def isBased(newPos: Int): Boolean = {
    newPos >= basePos && newPos < basePos + 4 && getFieldIndex(newPos) != -1
  }
}
