package tictactoe.domain

class TicTacToeGameSpec extends FlatSpec {

  "A TicTacToeGame" should " have a valid state in the beginning" in {
    val game = new TicTacToeGame
    val newGame = game.newGame()
    val gameState:GameState = newGame._1
    val validMoves:Seq[PlayerPosition] = newGame._2

    // for 3x3 board
    val expectedSize = 9
    assert(gameState.cells.size === expectedSize)
    assert(validMoves.size === expectedSize)
  }

  it should "have decremented 'validMoves' size after a move" in {
    val game = new TicTacToeGame
    val newGame = game.newGame()

    // for 3x3 board
    val expectedSize = 9
    var moveResult = game.playerMakesMove(newGame._1, PlayerPosition(Player.PlayerX, CellPosition(Top, Left)))
    assert(moveResult.gameState.cells.size === expectedSize)
    assert(moveResult.remainingCellsForMove.size === expectedSize - 1)

    moveResult = game.playerMakesMove(moveResult.gameState, PlayerPosition(Player.PlayerO, CellPosition(Top, Center)))
    assert(moveResult.gameState.cells.size === expectedSize)
    assert(moveResult.remainingCellsForMove.size === expectedSize - 2)
  }

  it should "switch 'GameStatus' to other player after each move" in {
    val game = new TicTacToeGame
    val newGame = game.newGame()

    var moveResult = game.playerMakesMove(newGame._1, PlayerPosition(Player.PlayerX, CellPosition(Top, Left)))
    assert(moveResult.gameStatus === GameStatus.PlayerOToMove)

    moveResult = game.playerMakesMove(moveResult.gameState, PlayerPosition(Player.PlayerO, CellPosition(Top, Center)))
    assert(moveResult.gameStatus === GameStatus.PlayerXToMove)
  }

  it should "switch 'GameStatus' to WIN if row or diagonal is populated with all Xs or 0s" in {
    val game = new TicTacToeGame

    var newGame = game.newGame()
    var firstMove = game.playerMakesMove(newGame._1, PlayerPosition(Player.PlayerX, CellPosition(Top, Left)))
    var secondMove = game.playerMakesMove(firstMove.gameState, PlayerPosition(Player.PlayerX, CellPosition(Top, Center)))
    var thirdMove = game.playerMakesMove(secondMove.gameState, PlayerPosition(Player.PlayerX, CellPosition(Top, Right)))
    assert(thirdMove.gameStatus === GameStatus.PlayerXWon)

    newGame = game.newGame()
    firstMove = game.playerMakesMove(newGame._1, PlayerPosition(Player.PlayerO, CellPosition(Top, Left)))
    secondMove = game.playerMakesMove(firstMove.gameState, PlayerPosition(Player.PlayerO, CellPosition(VerticalPosition.Center, Center)))
    thirdMove = game.playerMakesMove(secondMove.gameState, PlayerPosition(Player.PlayerO, CellPosition(Bottom, Right)))
    assert(thirdMove.gameStatus === GameStatus.PlayerOWon)
  }
}
