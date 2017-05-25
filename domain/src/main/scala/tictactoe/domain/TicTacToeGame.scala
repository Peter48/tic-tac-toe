package tictactoe.domain

import tictactoe.api.CellData._
import tictactoe.api.CellState._
import tictactoe.api.GameStatus._
import tictactoe.api.Player._
import tictactoe.api.{GameState, PlayerPosition, MoveResult, Cell, Line, CellPosition, CellState}


class TicTacToeGame {

  def newGame(): (GameState, Seq[PlayerPosition]) = {
    // create initial game state
    val gameState = GameState(emptyCells)

    // we assume PlayerX will start playing first
    val validMoves = allCellPositions.map(pos => PlayerPosition(PlayerX, pos))

    (gameState, validMoves)
  }

  def playerMakesMove(oldGameState: GameState, playedPosition: PlayerPosition): MoveResult = {

    val thisPlayer = playedPosition.player
    val cellValue = if (thisPlayer == PlayerX) X else O
    val newCell = Cell(playedPosition.cell, cellValue)
    val newGameState = updateGameState(newCell, oldGameState)

    // return MoveResult with the new state
    if (isGameWonBy(newGameState, thisPlayer)) {
      MoveResult(newGameState, if (thisPlayer == PlayerX) PlayerXWon else PlayerOWon, null)
    } else if (isGameTied(newGameState)) {
      MoveResult(newGameState, Tie, null)
    } else {
      MoveResult(newGameState, if (thisPlayer == PlayerX) PlayerOToMove else PlayerXToMove, remainingCellsForMove(newGameState))
    }
  }

  // update a given cell in the GameState and return a new GameState
  private def updateGameState(newCell: Cell, gameState: GameState): GameState = {
    // get a copy of the cells, with the new cell swapped in
    val newCells = gameState.cells.map(oldCell => if (oldCell.pos == newCell.pos) newCell else oldCell)
    // return a new game state with the new cells
    GameState(newCells)
  }

  // Return true if all cells have been played
  private def isGameTied(gameState: GameState): Boolean = {

    def cellWasPlayed(cell: Cell) = cell.state match {
      case X | O => true
      case Empty => false
    }

    gameState.cells.forall(cellState => cellWasPlayed(cellState))
  }

  // a list of 8 possible lines to check
  private val linesToCheck: Seq[Line] = {
    Seq(
        Line(topCells.map(cell=>cell.pos)),
        Line(centerCells.map(cell=>cell.pos)),
        Line(bottomCells.map(cell=>cell.pos)),

        Line(leftCells.map(cell=>cell.pos)),
        Line(middleCells.map(cell=>cell.pos)),
        Line(rightCells.map(cell=>cell.pos)),

        Line(diagonalCellPositions1),
        Line(diagonalCellPositions2))
  }

  private def isGameWonBy(gameState: GameState, player: Player) = {

    def getCell(posToFind: CellPosition, gameState: GameState) = {
      gameState.cells.find(cell => cell.pos == posToFind).get
    }

    def cellWasPlayedByPlayer(cell: Cell) = cell.state match {
      case CellState.X => (player == PlayerX)
      case CellState.O => (player == PlayerO)
      case CellState.Empty => false
    }

    def lineIsAllSamePlayer(line: Line) = {
      line.cells
        .map(cellPosition => getCell(cellPosition, gameState))
        .forall(cell => cellWasPlayedByPlayer(cell))
    }

    linesToCheck.exists(l => lineIsAllSamePlayer(l))
  }

  private def remainingCellsForMove(gameState: GameState): Seq[Cell] = {
    gameState.cells.filter(cell => cell.state == CellState.Empty)
  }
}
