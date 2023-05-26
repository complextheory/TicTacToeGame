package com.r4ziel.tiktactoegame

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

/**
 * Created by Jarvis Charles on 5/26/23.
 */
class GameFragmentViewModel(private val blockRepository: BlockRepository): ViewModel() {

    val blockLiveData = MutableLiveData<List<Block>>()
    private val blockList = mutableListOf<Block>()

    private var counter = 9

    fun getBlockData() {
        blockLiveData.value = generateBlocks()
    }

    private fun generateBlocks(): List<Block> {

        blockList.clear()
        do {
            blockList.add(Block("", counter))
            counter --
        } while (counter > 0)

        return blockList
    }
}