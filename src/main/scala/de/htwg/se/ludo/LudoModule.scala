package de.htwg.se.ludo

import com.google.inject.AbstractModule
import de.htwg.se.ludo.controller.controllerComponent.ControllerInterface
import de.htwg.se.ludo.controller.controllerComponent.controllerBaseImpl.Controller
import de.htwg.se.ludo.model.{AllPinWinStrategy, OnePinWinStrategy}
import de.htwg.se.ludo.model.diceComponent.{DiceInterface, dice6Impl, dice8Impl}
import de.htwg.se.ludo.model.gameComponent.GameInterface
import de.htwg.se.ludo.model.gameComponent.gameBaseImpl.Game
import de.htwg.se.ludo.util.WinStrategy
import net.codingwell.scalaguice.ScalaModule

class LudoModule extends AbstractModule with ScalaModule {
  // ControllerComponent
  bind[ControllerInterface].to[Controller]

  // GameComponent
  bind[GameInterface].to[Game]

  // DiceComponent
  bind[DiceInterface].annotatedWithName("D6").toInstance(new dice6Impl.Dice)
  bind[DiceInterface].annotatedWithName("D8").toInstance(new dice8Impl.Dice)

  bind[WinStrategy].annotatedWithName("OnePin").toInstance(new OnePinWinStrategy)
  bind[WinStrategy].annotatedWithName("AllPin").toInstance(new AllPinWinStrategy)
}
