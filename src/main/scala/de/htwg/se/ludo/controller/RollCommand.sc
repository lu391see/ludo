
import de.htwg.se.ludo.controller.Controller
import de.htwg.se.ludo.model.RandomDice
import de.htwg.se.ludo.util.Command

class RollCommand(controller: Controller) extends Command {
  var memento: Int = controller.pips
  override def doStep: Unit = {
    memento = controller.pips
    controller.pips = RandomDice().pips
    println(controller.currentPlayer + " throwed " + controller.pips)
  }

  override def undoStep: Unit = {
    val new_memento = controller.pips
    controller.pips = memento
    println(controller.currentPlayer + " throwed " + controller.pips)
    memento = new_memento
  }

  override def redoStep: Unit = undoStep
}