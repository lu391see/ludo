
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.diceComponent.dice6Impl.Dice
import de.htwg.se.ludo.util.Command

class RollCommand(controller: Controller) extends Command {
  var memento: Int = controller.pips
  override def doStep: Unit = {
    memento = controller.pips
    controller.pips = Dice().pips
    println(controller.currentPlayer.get + " throwed " + controller.pips)
  }

  override def undoStep: Unit = {
    val new_memento = controller.pips
    controller.pips = memento
    println(controller.currentPlayer.get + " throwed " + controller.pips)
    memento = new_memento
  }

  override def redoStep: Unit = undoStep
}