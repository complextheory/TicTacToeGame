package com.r4ziel.tiktactoegame.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jarvis Charles on 5/30/23.
 */

@Entity(tableName = "game-entity")
data class Game (

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "player_1")
    val player1: Player,

    @ColumnInfo(name = "player_2")
    val player2: Player,

    @ColumnInfo(name = "turn")
    val turn: Turn,

    @ColumnInfo(name = "player_1_block_list")
    val player1BlockList: BlockList,

    @ColumnInfo(name = "player_2_block_list")
    val player2BlockList: BlockList,

    @ColumnInfo(name = "draw_game_block_list")
    val drawGame: BlockList,

    @ColumnInfo(name = "is_game_in_progress")
    var isGameInProgress: Boolean?,

    @ColumnInfo(name = "is_game_over")
    var isGameOver: Boolean?,

    @ColumnInfo(name = "is_draw_game")
    var isDrawGame: Boolean?,

    @ColumnInfo(name = "winner")
    var winner: Player?
)