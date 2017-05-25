package tictactoe.api

import tictactoe.api.GameStatus.GameStatus

object GameStatus extends Enumeration {
  type GameStatus = Value
  val Blank, PlayerXToMove, PlayerOToMove, PlayerXWon, PlayerOWon, Tie = Value
}

case class GameState(cells: Seq[Cell])

case class MoveResult(gameState: GameState, gameStatus: GameStatus, remainingCellsForMove: Seq[Cell])