package de.htwg.se.ludo

import com.google.inject.AbstractModule
import com.google.inject.name.Names
import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}
import de.htwg.se.ludo.model.gameComponent.CellInterface
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl._
import de.htwg.se.ludo.util.WinStrategy
import net.codingwell.scalaguice.ScalaModule

class LudoModule extends AbstractModule with ScalaModule {
  // BasicBoardConstraint
  val defaultSize: Int = 72
  // BasicPlayerConstraint
  val maxPlayers = 4
  val minPlayers = 2
  val totalPins: Int = maxPlayers * 4

  override def configure(): Unit = {
    bind[ControllerInterface].to[Controller]

    bind[WinStrategy].annotatedWithName("OnePin").toInstance(new OnePinWinStrategy)
    bind[WinStrategy].annotatedWithName("AllPin").toInstance(new AllPinWinStrategy)

    bind[CellInterface].annotatedWith(Names.named("EmptyCell")).toInstance(Cell(""))

    bindConstant().annotatedWith(Names.named("DefaultSize")).to(defaultSize)
    bindConstant().annotatedWith(Names.named("TotalPins")).to(totalPins)
    bindConstant().annotatedWith(Names.named("MaxPlayers")).to(maxPlayers)

  }

}
