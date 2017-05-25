package tictactoe.api

import tictactoe.api.CellState.CellState
import tictactoe.api.HorizontalPosition.HorizontalPosition
import tictactoe.api.VerticalPosition.VerticalPosition


object HorizontalPosition extends Enumeration {
  type HorizontalPosition = Value
  val Left, Center, Right = Value
}

object VerticalPosition extends Enumeration {
  type VerticalPosition = Value
  val Top, Center, Bottom = Value
}

object CellState extends Enumeration {
  type CellState = Value
  val X, O, Empty = Value
}

case class CellMetaData(cell: Cell, title: String, index: Int)

case class CellPosition(vert: VerticalPosition, horiz: HorizontalPosition)

case class Cell(pos: CellPosition, state: CellState)

case class Line(cells: Seq[CellPosition])

object CellData {

  val allCellPositions = for {
    v <- VerticalPosition.values.toSeq
    h <- HorizontalPosition.values.toSeq
  } yield CellPosition(v, h)

  // create cells with 'Empty' initial state
  val emptyCells = allCellPositions.map(pos => Cell(pos, CellState.Empty))

  val topCells = emptyCells.filter(cell => cell.pos.vert == VerticalPosition.Top)
  val centerCells = emptyCells.filter(cell => cell.pos.vert == VerticalPosition.Center)
  val bottomCells = emptyCells.filter(cell => cell.pos.vert == VerticalPosition.Bottom)

  val leftCells = emptyCells.filter(cell => cell.pos.horiz == HorizontalPosition.Left)
  val middleCells = emptyCells.filter(cell => cell.pos.horiz == HorizontalPosition.Center)
  val rightCells = emptyCells.filter(cell => cell.pos.horiz == HorizontalPosition.Right)

  val diagonalCellPositions1 = Seq(CellPosition(VerticalPosition.Top, HorizontalPosition.Left),
                                   CellPosition(VerticalPosition.Center, HorizontalPosition.Center),
                                   CellPosition(VerticalPosition.Bottom, HorizontalPosition.Right))

  val diagonalCellPositions2 = Seq(CellPosition(VerticalPosition.Bottom, HorizontalPosition.Left),
                                   CellPosition(VerticalPosition.Center, HorizontalPosition.Center),
                                   CellPosition(VerticalPosition.Top, HorizontalPosition.Right))

  val cellMetaDataMap = (for (i <- emptyCells.indices)
    yield (emptyCells(i), CellMetaData(emptyCells(i), emptyCells(i).pos.toString, i))).toMap

}