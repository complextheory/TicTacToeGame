package com.r4ziel.tiktactoegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.r4ziel.tiktactoegame.database.GameDatabase
import com.r4ziel.tiktactoegame.entities.Block
import com.r4ziel.tiktactoegame.entities.BlockList
import com.r4ziel.tiktactoegame.entities.Player

/**
 * Created by Jarvis Charles on 5/26/23  Main Game ViewModel Responsible for Business Logic.
 */
class GameFragmentViewModel(private val savedState: SavedStateHandle, database: GameDatabase) : ViewModel() {

    var blockLiveData: MutableLiveData<List<Block>> =
        database.BlockDao().getAll() as MutableLiveData<List<Block>>

//    var blockLiveData: MutableLiveData<List<BlockEntity>> =
//        savedState.getLiveData<List<BlockEntity>>(BLOCK_LIST_KEY).switchMap {
//            MutableLiveData<List<BlockEntity>>()
//        } as MutableLiveData<List<BlockEntity>>


    var isGameOverLiveData: MutableLiveData<Boolean> =
        database.GameDao().getIsGameOver(false) as MutableLiveData<Boolean>

//    var isGameOverLiveData: MutableLiveData<Boolean> =
//        savedState.getLiveData<Boolean>(IS_GAME_OVER_KEY).switchMap {
//            MutableLiveData<Boolean>()
//        } as MutableLiveData<Boolean>

    var isDrawLiveData: MutableLiveData<Boolean> =
        database.GameDao().getIsDrawGame(false) as MutableLiveData<Boolean>

//    var isDrawLiveData: MutableLiveData<Boolean> =
//        savedState.getLiveData<Boolean>(IS_DRAW_KEY).switchMap {
//            MutableLiveData<Boolean>()
//        } as MutableLiveData<Boolean>

    private var blockList =
        blockLiveData.value ?: mutableListOf()

//    private var blockList =
//        savedState.get<MutableList<Block>>(BLOCK_LIST_KEY) ?: mutableListOf()

    private var player1BlockList =
        database.GameDao().getPlayer1BlockList(BlockList(0, mutableListOf(), "player1BlockList"))

//    private var player1BlockList =
//        savedState.get<MutableList<Int>>(PLAYER_1_LIST_KEY) ?: mutableListOf()

    private var player2BlockList =
        database.GameDao().getPlayer2BlockList()

//    private var player2BlockList =
//        savedState.get<MutableList<Int>>(PLAYER_2_LIST_KEY) ?: mutableListOf()

    private var drawGameBlockList =
        database.GameDao().getDrawGameBlockList()

//    private var drawGameBlockList =
//        savedState.get<MutableList<Int>>(DRAW_GAME_LIST_KEY) ?: mutableListOf()

    private var playerTurn: Int =
        if (database.GameDao().getTurns().size % 2 == 0) {
        2
    }else
        1

    private var player1 =
        database.GameDao().getPlayer1(Player(
            id = 1,
            playerName = "Tom P1"))

    private var player2 =
        database.GameDao().getPlayer2(Player(
            id = 2,
            playerName =  "Bill P2"
        ))

//    private var playerTurn: Int = savedState.get<Int>(PLAYER_TURN_KEY) ?: 1

    var winner: MutableLiveData<Player> = database.GameDao().getWinner(player1)?.value
//    var winner: Int = savedState.get<Int>(WINNER_KEY) ?: 0

    var isGameInProgress = database.GameDao().getIsGameInProgress(false)
//    var isGameInProgress = savedState.get<Boolean>(IS_GAME_IN_PROGRESS_KEY) ?: false

    private var counter = 1

    companion object {
        private const val BLOCK_LIST_KEY = "BLOCK_LIST_KEY"
        private const val PLAYER_1_LIST_KEY = "PLAYER_1_LIST_KEY"
        private const val PLAYER_2_LIST_KEY = "PLAYER_2_LIST_KEY"
        private const val DRAW_GAME_LIST_KEY = "DRAW_GAME_LIST_KEY"
        private const val PLAYER_TURN_KEY = "PLAYER_TURN_KEY"
        private const val IS_GAME_OVER_KEY = "IS_GAME_OVER_KEY"
        private const val IS_DRAW_KEY = "IS_DRAW_KEY"
        private const val WINNER_KEY = "WINNER_KEY"
        private const val IS_GAME_IN_PROGRESS_KEY = "IS_GAME_IN_PROGRESS_KEY"
    }

    fun startGame() {
        isGameInProgress = true
        blockLiveData.value = mutableListOf()
        isGameOverLiveData.value = false
        isDrawLiveData.value = false
        blockLiveData.postValue(generateBlocks())
        updateSavedState()
    }

    fun updateTurn(block: Block) {
        blockLiveData.value?.get(block.id - 1)?.isClicked = true

        if (playerTurn == 1) {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "X"
            blockLiveData.value?.get(block.id - 1)?.playerNumber = 1

            player1BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 2
        } else {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "O"
            blockLiveData.value?.get(block.id - 1)?.playerNumber = 2
            player2BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 1
        }

        blockLiveData.postValue(blockLiveData.value)
        isGameOverLiveData.postValue(isGameOver())
        isDrawLiveData.postValue(isDrawGame())
        updateSavedState()
    }

    /**
     * Method for clearing board and repopulating
     */

    fun generateBlocks(): List<Block> {
        player1BlockList.clear()
        player2BlockList.clear()
        drawGameBlockList.clear()
        blockList.clear()

        do {
            blockList.add(Block("", counter, false, 0))
            counter ++
        } while (counter < 10)

        //Reset Values For Next Game
        playerTurn = 1
        counter = 1
        updateSavedState()
        return blockList
    }

    private fun updateSavedState() {
        savedState[IS_GAME_IN_PROGRESS_KEY] = isGameInProgress
        savedState[PLAYER_1_LIST_KEY] = player1BlockList
        savedState[PLAYER_2_LIST_KEY] = player2BlockList
        savedState[PLAYER_TURN_KEY] = playerTurn
        savedState[DRAW_GAME_LIST_KEY] = drawGameBlockList
        savedState[IS_DRAW_KEY] = isDrawLiveData.value
        savedState[IS_GAME_OVER_KEY] = isGameOverLiveData.value
        savedState[BLOCK_LIST_KEY] = blockLiveData.value
    }

    /**
     * Methods Determining Win Lose Or Draw
     */

    fun isDrawGame(): Boolean {
        return drawGameBlockList.size == 9 && !isGameOver()
    }

    fun isGameOver(): Boolean {

        winner = if (player1BlockList.size == player2BlockList.size) {
            2
        } else
            1

        return  (player1BlockList.contains(1) && player1BlockList.contains(2) && player1BlockList.contains(3)) ||
                (player1BlockList.contains(4) && player1BlockList.contains(5) && player1BlockList.contains(6)) ||
                (player1BlockList.contains(7) && player1BlockList.contains(8) && player1BlockList.contains(9)) ||
                (player1BlockList.contains(1) && player1BlockList.contains(4) && player1BlockList.contains(7)) ||
                (player1BlockList.contains(2) && player1BlockList.contains(5) && player1BlockList.contains(8)) ||
                (player1BlockList.contains(3) && player1BlockList.contains(6) && player1BlockList.contains(9)) ||
                (player1BlockList.contains(1) && player1BlockList.contains(5) && player1BlockList.contains(9)) ||
                (player1BlockList.contains(3) && player1BlockList.contains(5) && player1BlockList.contains(7)) ||
                (player2BlockList.contains(1) && player2BlockList.contains(2) && player2BlockList.contains(3)) ||
                (player2BlockList.contains(4) && player2BlockList.contains(5) && player2BlockList.contains(6)) ||
                (player2BlockList.contains(7) && player2BlockList.contains(8) && player2BlockList.contains(9)) ||
                (player2BlockList.contains(1) && player2BlockList.contains(4) && player2BlockList.contains(7)) ||
                (player2BlockList.contains(2) && player2BlockList.contains(5) && player2BlockList.contains(8)) ||
                (player2BlockList.contains(3) && player2BlockList.contains(6) && player2BlockList.contains(9)) ||
                (player2BlockList.contains(1) && player2BlockList.contains(5) && player2BlockList.contains(9)) ||
                (player2BlockList.contains(3) && player2BlockList.contains(5) && player2BlockList.contains(7))
    }
}