package tictactoe.api

import tictactoe.api.Player.Player

object Player extends Enumeration {
  type Player = Value
  val PlayerX, PlayerO = Value
}

object PlayerAction extends Enumeration {
  type PlayerAction = Value
  val ContinuePlay, ExitGame = Value
}

case class PlayerPosition(player: Player, cell: CellPosition)
