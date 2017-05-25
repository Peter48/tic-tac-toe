package tictactoe.console

import tictactoe.api.Player.Player
import tictactoe.api.PlayerAction.PlayerAction
import tictactoe.api._
import tictactoe.console.ConsoleGameHelper._
import tictactoe.domain.TicTacToeGame

import scala.io.StdIn

object ConsoleGameMain {

  val api = new TicTacToeGame
  val newGame = api.newGame()
  val newGameState = newGame._1
  val firstMove = MoveResult(newGameState, GameStatus.PlayerXToMove, newGameState.cells)

  def main(args: Array[String]) {
    println("\nWelcome to Tic-Tac-Toe. Please make your move selection by entering a number" +
      " 1-%s corresponding to the movement option on the right.\n".format(firstMove.gameState.cells.size))

    gameLoop(api, PlayerAction.ContinuePlay, firstMove)
  }

  // main game loop, executed for each player-action
  private def gameLoop(api: TicTacToeGame, playerAction: PlayerAction, moveResult: MoveResult): Unit = {

    playerAction match {
      case PlayerAction.ExitGame => println("Exiting game.")
      case PlayerAction.ContinuePlay =>

        val gameState: GameState = moveResult.gameState
        val availableMoves: Seq[Cell] = moveResult.remainingCellsForMove

        // update the display
        displayCells(gameState.cells)

        moveResult.gameStatus match {
          case GameStatus.Tie =>
            println("GAME OVER - Tie")
            println("")
            gameLoop(api, askToPlayAgain(api), firstMove)
          case GameStatus.PlayerXWon =>
            println("GAME WON by PlayerX. You have beaten our AI!")
          case GameStatus.PlayerOWon =>
            println("GAME WON by PlayerO")
            println("")
            gameLoop(api, askToPlayAgain(api), firstMove)
          case GameStatus.PlayerOToMove =>
            println("You have put an X. I will put an O") // We assume that Player O is our AI
            val input = CellData.cellMetaDataMap(availableMoves.head).index.toString
            val newResult = processMoveIndex(input, gameState, availableMoves, Player.PlayerO, api.playerMakesMove, null)
            gameLoop(api, newResult._1, newResult._2)
          case GameStatus.PlayerXToMove =>
            val newResult = processInput(gameState, availableMoves, Player.PlayerX, api.playerMakesMove)
            gameLoop(api, newResult._1, newResult._2)
        }
    }
  }

  // Ask player for input. Process the entered string as a move index or a "q" command
  private def processInput(gameState: GameState, availableMoves: Seq[Cell], player: Player,
                           makeMove: (GameState, PlayerPosition) => MoveResult): (PlayerAction, MoveResult) = {

    // helper that calls this function again with exactly same parameters
    def processInputAgain() = processInput(gameState, availableMoves, player, makeMove)

    println("Where to? Enter an int corresponding to a displayed move or q to quit:")
    val inputStr = StdIn.readLine()
    if (inputStr == "q") {
      (PlayerAction.ExitGame, null)
    } else {
      processMoveIndex(inputStr, gameState, availableMoves, player, makeMove, processInputAgain)
    }
  }

  private def processMoveIndex(inputStr: String,
                               gameState: GameState,
                               availableMoves: Seq[Cell],
                               player: Player,
                               makeMove: (GameState, PlayerPosition) => MoveResult,
                               processInputAgain: () => (PlayerAction, MoveResult)): (PlayerAction, MoveResult) = {

    object Int {
      def unapply(s: String): Option[Int] = try {
        Some(s.toInt)
      } catch {
        case _: java.lang.NumberFormatException => None
      }
    }

    inputStr match {
      case Int(inputIndex) =>
        val move: Option[Cell] = gameState.cells.lift(inputIndex)
        move match {
          case Some(m) =>
            if (!availableMoves.contains(m)) {
              println("...No move found for inputIndex %s. Try again".format(inputIndex))
              processInputAgain()
            }
            // corresponding move found, so make a move
            val moveResult = makeMove(gameState, PlayerPosition(player, m.pos))
            (PlayerAction.ContinuePlay, moveResult) // return it
          case None =>
            // no corresponding move found
            println("...No move found for inputIndex %s. Try again".format(inputIndex))
            // try again
            processInputAgain()
        }
      case _ =>
        println("...Please enter a valid integer corresponding to a displayed move.")
        processInputAgain()
    }
  }
}

