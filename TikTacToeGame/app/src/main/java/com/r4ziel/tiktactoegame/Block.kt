package com.r4ziel.tiktactoegame

/**
 * Created by Jarvis Charles on 5/26/23.
 */
data class Block (

    var xOrO: String,
    val id: Int,
    var isClicked: Boolean,
    // Prince Note: If this is just the player, I would name this player. Adding clicked in the name confuses things
    var playerClicked: Int

)
