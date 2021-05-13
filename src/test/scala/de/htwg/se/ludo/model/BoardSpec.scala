package de.htwg.se.ludo.model
import org.scalatest.wordspec.AnyWordSpec
import org.scalatest.matchers.should.Matchers
// import org.scalatest.{Matchers, WordSpec}

class BoardSpec extends AnyWordSpec with Matchers {
  "Matrix is an immutable data type that contains a two-dimentional Vector of Cells."
  "A Matrix" when {
    "empty " should {
      "be created by using a demension and a sample cell" in {
        val matrix = new Board(2, EmptyCell(), 4)
        matrix.size should be(2)
      }
      "for test purposes only be created with a Vector of Vectors" in {
        val testMatrix = Board(Vector(EmptyCell()), 4)
        testMatrix.size should be(1)
      }
    }
    "filled" should {
      val matrix = new Board(2, new Cell("Y1"), 4)
      "give access to its cells" in {
        matrix.cell(0) should be(new Cell("Y1"))
        matrix.cell(1) should be(new Cell("Y1"))
      }
      "replace cells and return a new data structure" in {
        val returnedMatrix = matrix.replaceCell(0, new Cell("Y2"))
        matrix.cell(0) should be(new Cell("Y1"))
        returnedMatrix.cell(0) should be(new Cell("Y2"))
        returnedMatrix.cell(1) should be(new Cell("Y1"))
      }
      "be filled using fill operation" in {
        val returnedMatrix = matrix.fill(new Cell("B1"))
        returnedMatrix.cell(0) should be(new Cell("B1"))
        returnedMatrix.cell(1) should be(new Cell("B1"))
      }
      "String contain only 5s" in {
        matrix.toString contains "[5]"
      }
    }
  }
}
