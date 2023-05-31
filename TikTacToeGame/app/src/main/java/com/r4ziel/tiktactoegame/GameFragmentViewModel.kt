package com.r4ziel.tiktactoegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap
import com.r4ziel.tiktactoegame.database.GameDatabase
import com.r4ziel.tiktactoegame.entities.Block
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

/**
 * Created by Jarvis Charles on 5/26/23  Main Game ViewModel Responsible for Business Logic.
 */
class GameFragmentViewModel(private val savedState: SavedStateHandle, private val database: GameDatabase) : ViewModel() {

    var blockLiveData: MutableLiveData<List<Block>> =
        database.BlockDao().getAll()

    var isGameOverLiveData: MutableLiveData<Boolean> =
        savedState.getLiveData<Boolean>(IS_GAME_OVER_KEY).switchMap {
            MutableLiveData<Boolean>()
        } as MutableLiveData<Boolean>


    var isDrawLiveData: MutableLiveData<Boolean> =
        savedState.getLiveData<Boolean>(IS_DRAW_KEY).switchMap {
            MutableLiveData<Boolean>()
        } as MutableLiveData<Boolean>

    private lateinit var blockList: MutableList<Block>

//    private var blockList =
//        savedState.get<MutableList<Block>>(BLOCK_LIST_KEY) ?: mutableListOf()

    private var player1BlockList =
        savedState.get<MutableList<Int>>(PLAYER_1_LIST_KEY) ?: mutableListOf()

    private var player2BlockList =
        savedState.get<MutableList<Int>>(PLAYER_2_LIST_KEY) ?: mutableListOf()

    private var drawGameBlockList =
        savedState.get<MutableList<Int>>(DRAW_GAME_LIST_KEY) ?: mutableListOf()

    private var playerTurn: Int = 1


//    private var playerTurn: Int = savedState.get<Int>(PLAYER_TURN_KEY) ?: 1

    var winner: Int = savedState.get<Int>(WINNER_KEY) ?: 0

//    var isGameInProgress = !isDrawGame() && winner() != 3

    var isGameInProgress = winner() != PLAYER_1_KEY && winner() != PLAYER_2_KEY


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
        private const val PLAYER_1_BLOCK_KEY = 3
        private const val PLAYER_2_BLOCK_KEY = -3
        private const val PLAYER_1_KEY = 1
        private const val PLAYER_2_KEY = -1
        private const val IS_DRAW_GAME_KEY = 0
        private const val LIST_FULL_KEY = 8
    }

    fun startGame() {

        GlobalScope.launch {
            blockList.clear()
            database.BlockDao().updateBlockList(blockList)
            blockLiveData.value = mutableListOf()
            blockLiveData.postValue(generateBlocks())
            database.BlockDao().updateBlockList(blockLiveData.value as MutableList<Block>)
            isGameOverLiveData.value = false
            isDrawLiveData.value = false

        }
        updateSavedState()
    }

    fun updateTurn(block: Block) {
        blockLiveData.value?.get(block.id - 1)?.isClicked = true

        if (playerTurn == 1) {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "X"
            blockLiveData.value?.get(block.id - 1)?.playerNumber = PLAYER_1_KEY

            database.PlayerDao().insertBlock(block.id)
//            player1BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 2
        } else {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "O"
            blockLiveData.value?.get(block.id - 1)?.playerNumber = PLAYER_2_KEY

            database.PlayerDao().insertBlock(block.id)
//            player2BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 1
        }

        blockLiveData.postValue(blockLiveData.value)
        database.BlockDao().updateBlockList(blockLiveData.value as MutableList<Block>)

        when(winner()){
            PLAYER_1_KEY or PLAYER_2_KEY-> isGameOverLiveData.postValue(true)
            IS_DRAW_GAME_KEY -> isDrawLiveData.postValue(true)
        }
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
        database.BlockDao().updateBlockList(blockList)
        database.PlayerDao().updateBlockList(player1BlockList)
        database.PlayerDao().updateBlockList(player2BlockList)

        do {
            blockList.add(Block(counter, 0, "", false))
            counter ++
        } while (counter < 10)

        //Reset Values For Next Game
        playerTurn = 1
        counter = 1
        updateSavedState()
        return blockList
    }

    private fun updateSavedState() {
        database.PlayerDao().updateBlockList(player1BlockList)
        database.PlayerDao().updateBlockList(player2BlockList)

        database.PlayerDao().updateBlockList(drawGameBlockList)

        drawGameBlockList.clear()
        drawGameBlockList.addAll(player1BlockList)
        drawGameBlockList.addAll(player2BlockList)
        database.BlockDao().updateBlockList(blockLiveData.value as MutableList<Block>)
        savedState[IS_GAME_IN_PROGRESS_KEY] = isGameInProgress
//        savedState[PLAYER_1_LIST_KEY] = player1BlockList
//        savedState[PLAYER_2_LIST_KEY] = player2BlockList
        savedState[PLAYER_TURN_KEY] = playerTurn
//        savedState[DRAW_GAME_LIST_KEY] = drawGameBlockList
        savedState[IS_DRAW_KEY] = isDrawLiveData.value
        savedState[IS_GAME_OVER_KEY] = isGameOverLiveData.value
//        savedState[BLOCK_LIST_KEY] = blockLiveData.value
    }

    /**
     * Methods Determining Win Lose Or Draw
     */

//    fun isDrawGame(): Boolean {
//        return drawGameBlockList.size == 9 && !winner()
//    }


    fun winner(): Int {

        player1BlockList.sort()
        player2BlockList.sort()

        val winnerSetList = listOf(
            player1BlockList.sumOf { (player1BlockList[1] + player1BlockList[2] + player1BlockList[3]) },
            player1BlockList.sumOf { (player1BlockList[4] + player1BlockList[5] + player1BlockList[6]) },
            player1BlockList.sumOf { (player1BlockList[7] + player1BlockList[8] + player1BlockList[9]) },
            player1BlockList.sumOf { (player1BlockList[1] + player1BlockList[4] + player1BlockList[7]) },
            player1BlockList.sumOf { (player1BlockList[2] + player1BlockList[5] + player1BlockList[8]) },
            player1BlockList.sumOf { (player1BlockList[3] + player1BlockList[6] + player1BlockList[9]) },
            player1BlockList.sumOf { (player1BlockList[1] + player1BlockList[5] + player1BlockList[9]) },
            player1BlockList.sumOf { (player1BlockList[3] + player1BlockList[5] + player1BlockList[7]) },
            player2BlockList.sumOf { (player2BlockList[1] + player2BlockList[2] + player2BlockList[3]) },
            player2BlockList.sumOf { (player2BlockList[4] + player2BlockList[5] + player2BlockList[6]) },
            player2BlockList.sumOf { (player2BlockList[7] + player2BlockList[8] + player2BlockList[9]) },
            player2BlockList.sumOf { (player2BlockList[1] + player2BlockList[4] + player2BlockList[7]) },
            player2BlockList.sumOf { (player2BlockList[2] + player2BlockList[5] + player2BlockList[8]) },
            player2BlockList.sumOf { (player2BlockList[3] + player2BlockList[6] + player2BlockList[9]) },
            player2BlockList.sumOf { (player2BlockList[1] + player2BlockList[5] + player2BlockList[9]) },
            player2BlockList.sumOf { (player2BlockList[3] + player2BlockList[5] + player2BlockList[7]) }
        )

        if (winnerSetList.contains(PLAYER_1_BLOCK_KEY) || winnerSetList.contains(PLAYER_2_BLOCK_KEY) || drawGameBlockList.size == LIST_FULL_KEY) {

            winner = if (player1BlockList.size == player2BlockList.size) {
                PLAYER_1_KEY + 1
            } else
                PLAYER_1_KEY

            for (set in winnerSetList) {
                return when (set) {
                    PLAYER_1_BLOCK_KEY -> PLAYER_1_KEY
                    PLAYER_2_BLOCK_KEY -> PLAYER_2_KEY
                    else -> 0
                }
            }
        }

        return 2
    }
}