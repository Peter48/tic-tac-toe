package tictactoe.akka

import akka.actor.Actor

object Greeter {
  case object Greet
  case object Done
}

class Greeter extends Actor {
  def receive = {
    case Greeter.Greet =>
      println("Hello World Akka's version of TicTacToe")
      sender() ! Greeter.Done
  }
}