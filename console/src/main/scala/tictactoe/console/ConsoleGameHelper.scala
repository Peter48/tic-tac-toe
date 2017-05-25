package tictactoe.console

import tictactoe.api.PlayerAction.PlayerAction
import tictactoe.api._
import tictactoe.domain.TicTacToeGame

import scala.io.StdIn

object ConsoleGameHelper {

  // Display the cells on the console in a grid
  def displayCells(cells: Seq[Cell]): Unit = {

    val indentation = "        "

    def cellStateToStr(cell: Cell): String = {
      cell.state match {
        case CellState.Empty => " "
        case CellState.X => "X"
        case CellState.O => "O"
      }
    }

    def cellIndexToStr(metaData: Option[CellMetaData]): String = {
      metaData match {
        case Some(m) =>
          m.cell.state match {
            case CellState.Empty => m.index.toString
            case _ => " "
          }
        case None => " "
      }
    }

    def printCellPairs(seq: Seq[String]) = seq.reduceLeft[String] { (s1, s2) => s1 + "|" + s2 }

    def printCells(cells: Seq[Cell]): Unit = {
      val cellState = printCellPairs(cells.map(cell => cellStateToStr(cell)))
      val cellIndex = printCellPairs(cells.map(cell => cellIndexToStr(CellData.cellMetaDataMap.get(cell))))
      println(cellState + indentation + cellIndex)
    }

    println("Board" + indentation + "Movement Options")
    printCells(cells.filter(cell => cell.pos.vert == VerticalPosition.Top))
    printCells(cells.filter(cell => cell.pos.vert == VerticalPosition.Center))
    printCells(cells.filter(cell => cell.pos.vert == VerticalPosition.Bottom))
    println("\n") // add some space
  }

  // After each game is finished,
  // ask whether to play again.
  def askToPlayAgain(api: TicTacToeGame): PlayerAction = {
    println("Would you like to play again (y/n)?")
    val inputStr = StdIn.readLine()
    inputStr match {
      case "y" => PlayerAction.ContinuePlay //api.newGame()
      case "n" => PlayerAction.ExitGame
      case _ => askToPlayAgain(api)
    }
  }
}
