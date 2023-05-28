package com.r4ziel.tiktactoegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.switchMap

/**
 * Created by Jarvis Charles on 5/26/23  Main Game ViewModel Responsible for Business Logic.
 */
class GameFragmentViewModel(private val savedState: SavedStateHandle) : ViewModel() {

    var blockLiveData: MutableLiveData<List<Block>> =
        savedState.getLiveData<List<Block>>(BLOCK_LIST_KEY).switchMap {
            MutableLiveData<List<Block>>()
        } as MutableLiveData<List<Block>>

    var isGameOverLiveData: MutableLiveData<Boolean> =
        savedState.getLiveData<Boolean>(IS_GAME_OVER_KEY).switchMap {
            MutableLiveData<Boolean>()
    } as MutableLiveData<Boolean>

    var isDrawLiveData: MutableLiveData<Boolean> = savedState.getLiveData<Boolean>(IS_DRAW_KEY).switchMap {
        MutableLiveData<Boolean>()
    } as MutableLiveData<Boolean>

    private var blockList: MutableList<Block> =
        savedState.get<MutableList<Block>>(BLOCK_LIST_KEY) ?: mutableListOf()
    var player1BlockList: MutableList<Int> =
        savedState.get<MutableList<Int>>(PLAYER_1_LIST_KEY) ?: mutableListOf()
    var player2BlockList: MutableList<Int> =
        savedState.get<MutableList<Int>>(PLAYER_2_LIST_KEY) ?: mutableListOf()
    var drawGameBlockList: MutableList<Int> =
        savedState.get<MutableList<Int>>(DRAW_GAME_LIST_KEY) ?: mutableListOf()
    var playerTurn: Int =
        savedState.get<Int>(PLAYER_TURN_KEY) ?: 1
    var winner: Int =
        savedState.get<Int>(WINNER_KEY) ?: 0
    var isGameInProgress: Boolean =
        savedState.get<Boolean>(IS_GAME_IN_PROGRESS_KEY) ?: false

    private var counter = 1

    companion object {
        private const val BLOCK_LIST_KEY = "BLOCK_LIST_KEY"
        private const val PLAYER_1_LIST_KEY = "PLAYER_1_LIST_KEY"
        private const val PLAYER_2_LIST_KEY = "PLAYER_2_LIST_KEY"
        private const val DRAW_GAME_LIST_KEY = "DRAW_GAME_LIST_KEY"
        private const val PLAYER_TURN_KEY = "PLAYER_TURN_KEY"
        private const val IS_GAME_OVER_KEY = "IS_GAME_OVER"
        private const val IS_DRAW_KEY = "IS_DRAW_KEY"
        private const val WINNER_KEY = "WINNER"
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
            blockLiveData.value?.get(block.id - 1)?.player = 1
            player1BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 2
        } else {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "O"
            blockLiveData.value?.get(block.id - 1)?.player = 2
            player2BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 1
        }

        blockLiveData.value = blockLiveData.value
        isGameOverLiveData.postValue(isGameOver())
        isDrawLiveData.postValue(isDrawGame())
        updateSavedState()
    }

    /**
     * Method for clearing board and repopulating
     */

    private fun generateBlocks(): List<Block> {
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