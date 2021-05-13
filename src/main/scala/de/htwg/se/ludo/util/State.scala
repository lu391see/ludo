package de.htwg.se.ludo.util

trait State[T] {
  def handle(string: String, n: T)
}
