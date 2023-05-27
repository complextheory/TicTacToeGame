package com.r4ziel.tiktactoegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jarvis Charles on 5/26/23.
 */
class GameFragmentViewModel : ViewModel() {

    var blockLiveData = MutableLiveData<List<Block>>()
    var isGameOverLiveData = MutableLiveData<Boolean>()
    var isDrawLiveData = MutableLiveData<Boolean>()
    private var blockList = mutableListOf<Block>()
    private var player1BlockList = mutableListOf<Int>()
    private var player2BlockList = mutableListOf<Int>()
    private var drawGameBlockList = mutableListOf<Int>()
    private var playerTurn = 1
    var winner = 0

    private var counter = 1

    fun startGame() {
        blockLiveData.value = mutableListOf()
        isGameOverLiveData.value = false
        isDrawLiveData.value = false
        blockLiveData.postValue(generateBlocks())
    }

    fun updateTurn(block: Block) {
        blockLiveData.value?.get(block.id - 1)?.isClicked = true

        if (playerTurn == 1) {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "X"
            blockLiveData.value?.get(block.id - 1)?.playerClicked = 1
            player1BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 2
        } else {
            blockLiveData.value?.get(block.id - 1)?.xOrO = "O"
            blockLiveData.value?.get(block.id - 1)?.playerClicked = 2
            player2BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            playerTurn = 1
        }

        blockLiveData.value = blockLiveData.value
        isGameOverLiveData.postValue(isGameOver())
        isDrawLiveData.postValue(isDrawGame())

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
        return blockList
    }

    /**
     * Methods Determining Win Lose Or Draw
     */

    fun isDrawGame(): Boolean {
        return drawGameBlockList.contains(1) && drawGameBlockList.contains(2) && drawGameBlockList.contains(3) &&
            drawGameBlockList.contains(4) && drawGameBlockList.contains(5) && drawGameBlockList.contains(6) &&
            drawGameBlockList.contains(7) && drawGameBlockList.contains(8) && drawGameBlockList.contains(9)
    }

    fun isGameOver(): Boolean {

        if ((player1BlockList.contains(1) && player1BlockList.contains(2) && player1BlockList.contains(3)) ||
            (player1BlockList.contains(4) && player1BlockList.contains(5) && player1BlockList.contains(6)) ||
            (player1BlockList.contains(7) && player1BlockList.contains(8) && player1BlockList.contains(9)) ||
            (player1BlockList.contains(1) && player1BlockList.contains(4) && player1BlockList.contains(7)) ||
            (player1BlockList.contains(2) && player1BlockList.contains(5) && player1BlockList.contains(8)) ||
            (player1BlockList.contains(3) && player1BlockList.contains(6) && player1BlockList.contains(9)) ||
            (player1BlockList.contains(1) && player1BlockList.contains(5) && player1BlockList.contains(9)) ||
            (player1BlockList.contains(3) && player1BlockList.contains(5) && player1BlockList.contains(7))) {

            winner = 1
            return true
        } else if ((player2BlockList.contains(1) && player2BlockList.contains(2) && player2BlockList.contains(3)) ||
            (player2BlockList.contains(4) && player2BlockList.contains(5) && player2BlockList.contains(6)) ||
            (player2BlockList.contains(7) && player2BlockList.contains(8) && player2BlockList.contains(9)) ||
            (player2BlockList.contains(1) && player2BlockList.contains(4) && player2BlockList.contains(7)) ||
            (player2BlockList.contains(2) && player2BlockList.contains(5) && player2BlockList.contains(8)) ||
            (player2BlockList.contains(3) && player2BlockList.contains(6) && player2BlockList.contains(9)) ||
            (player2BlockList.contains(1) && player2BlockList.contains(5) && player2BlockList.contains(9)) ||
            (player2BlockList.contains(3) && player2BlockList.contains(5) && player2BlockList.contains(7))) {

            winner = 2
            return true
        } else
            return false
    }
}