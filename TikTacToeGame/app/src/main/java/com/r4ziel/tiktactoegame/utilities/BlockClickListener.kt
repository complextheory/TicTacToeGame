package com.r4ziel.tiktactoegame.utilities

import com.r4ziel.tiktactoegame.entities.Block

/**
 * Created by Jarvis Charles on 5/26/23.
 */
interface BlockClickListener {
    fun onBlockClicked(block: Block)
}