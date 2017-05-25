package services

import javax.inject._

import tictactoe.api.PlayerAction.PlayerAction
import tictactoe.api._
import tictactoe.domain.TicTacToeGame

/**
  * This trait demonstrates how to create a component that is injected into a controller.
  */
trait WebGameService {
  def nextMove(): Unit
}

@Singleton
class WebGameServiceImpl extends WebGameService {

  val api = new TicTacToeGame
  val newGameState = api.newGame()._1
  val firstMove = MoveResult(newGameState, GameStatus.PlayerXToMove, newGameState.cells)

  override def nextMove(): Unit = gameLoop(api, PlayerAction.ContinuePlay, firstMove)

  // main game loop, executed for each player-action
  private def gameLoop(api: TicTacToeGame, playerAction: PlayerAction, moveResult: MoveResult): Unit = {

    playerAction match {
      case PlayerAction.ContinuePlay =>

        val gameState: GameState = moveResult.gameState
        val availableMoves: Seq[Cell] = moveResult.remainingCellsForMove

        // update the display
    }
  }
}
