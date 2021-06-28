package de.htwg.se.ludo.model.gameComponent.gameBaseImpl

import org.scalatest.matchers.should.Matchers
import org.scalatest.wordspec.AnyWordSpec

class BoardSpec extends AnyWordSpec with Matchers {
  "A Matrix" when {
    "empty " should {
      "be created by using a demension and a sample cell" in {
        val matrix = new Board(2, Cell(""), 0)
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Board(Vector(Cell("")), 0)
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val cell = Cell("Y1")
      val matrix = new Board(2, cell, 0)
      "give access to its cells" in {
        matrix.cell(0).toString should be("[Y1]")
        matrix.cell(1) should be(cell)
      }
      "replace cells and return a new data structure" in {
        val newcell = Cell("Y2")
        val returnedMatrix = matrix.replaceCell(0, newcell)
        matrix.cell(0) should be(cell)
        returnedMatrix.cell(0) should be(Cell("Y2"))
        returnedMatrix.cell(1) should be(Cell("Y1"))
      }
      "String contain only 5s" in {
        matrix.toString contains "[B1]"
      }
    }
  }
}
