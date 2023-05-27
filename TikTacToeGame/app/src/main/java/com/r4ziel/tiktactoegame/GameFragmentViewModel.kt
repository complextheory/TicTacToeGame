package com.r4ziel.tiktactoegame

import android.app.AlertDialog
import androidx.core.app.ActivityCompat.finishAffinity
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import kotlin.system.exitProcess

/**
 * Created by Jarvis Charles on 5/26/23.
 */
class GameFragmentViewModel(): ViewModel() {

    var blockLiveData = MutableLiveData<List<Block>>()
    var isGameOverLiveData = MutableLiveData<Boolean>()
    private val blockList = mutableListOf<Block>()
    var player1BlockList = mutableListOf<Int>()
    var player2BlockList = mutableListOf<Int>()
    var drawGameBlockList = mutableListOf<Int>()
    var playerTurn = 1
    var winner = 0
    var isDraw = false

    private var counter = 0

    fun getBlockData() {
        blockLiveData.value = generateBlocks()
    }

    fun updateBlock(block: Block) {
        blockLiveData.value?.get(block.id)?.isClicked = true
        if (playerTurn == 1) {
            blockLiveData.value?.get(block.id)?.xOrO = "X"
            player1BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            isGameOverLiveData.value = isGameOver()
            playerTurn = 2

        } else {
            blockLiveData.value?.get(block.id)?.xOrO = "O"
            player2BlockList.add(block.id)
            drawGameBlockList.add(block.id)
            isGameOverLiveData.value = isGameOver()
            playerTurn = 1
        }

        blockLiveData.value = blockLiveData.value

    }

    fun isGameOver(): Boolean {

        if (player1BlockList.contains(1) && player1BlockList.contains(2) && player1BlockList.contains(3) ||
            player1BlockList.contains(4) && player1BlockList.contains(5) && player1BlockList.contains(6) ||
            player1BlockList.contains(7) && player1BlockList.contains(8) && player1BlockList.contains(9) ||
            player1BlockList.contains(1) && player1BlockList.contains(4) && player1BlockList.contains(7) ||
            player1BlockList.contains(3) && player1BlockList.contains(6) && player1BlockList.contains(9) ||
            player1BlockList.contains(1) && player1BlockList.contains(5) && player1BlockList.contains(9) ||
            player1BlockList.contains(3) && player1BlockList.contains(5) && player1BlockList.contains(7)) {

            winner = 1
            return true
        } else if (player2BlockList.contains(1) && player2BlockList.contains(2) && player2BlockList.contains(3) ||
            player2BlockList.contains(4) && player2BlockList.contains(5) && player2BlockList.contains(6) ||
            player2BlockList.contains(7) && player2BlockList.contains(8) && player2BlockList.contains(9) ||
            player2BlockList.contains(1) && player2BlockList.contains(4) && player2BlockList.contains(7) ||
            player2BlockList.contains(3) && player2BlockList.contains(6) && player2BlockList.contains(9) ||
            player2BlockList.contains(1) && player2BlockList.contains(5) && player2BlockList.contains(9) ||
            player2BlockList.contains(3) && player2BlockList.contains(5) && player2BlockList.contains(7)) {

            winner = 2
            return true
        } else if (drawGameBlockList.contains(1) && drawGameBlockList.contains(2) && drawGameBlockList.contains(3) &&
            drawGameBlockList.contains(4) && drawGameBlockList.contains(5) && drawGameBlockList.contains(6) &&
            drawGameBlockList.contains(7) && drawGameBlockList.contains(8) && drawGameBlockList.contains(9)) {

            isDraw = true
            return true
        }else
            return false
    }

    private fun generateBlocks(): List<Block> {

        blockList.clear()
        do {
            blockList.add(Block("", counter, false))
            counter ++
        } while (counter < 9)

        return blockList
    }
}