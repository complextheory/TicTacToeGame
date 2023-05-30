package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Query
import androidx.lifecycle.LiveData
import com.r4ziel.tiktactoegame.entities.Block
import com.r4ziel.tiktactoegame.entities.BlockList
import com.r4ziel.tiktactoegame.entities.Player
import com.r4ziel.tiktactoegame.entities.Turn

/**
 * Created by Jarvis Charles on 5/30/23.
 */
interface GameDao {

    @Query ("SELECT * FROM  `game-entity`")
    fun getTurns(): List<Turn>

    @Query("SELECT * FROM `game-entity` WHERE player_1 LIKE :player1")
    fun getPlayer1(player1: Player): LiveData<Player>

    @Query("SELECT * FROM `game-entity` WHERE player_2 LIKE :player2")
    fun getPlayer2(player2: Player): LiveData<Player>

    @Query("SELECT * FROM `game-entity` WHERE player_1_block_list LIKE :player1BlockList")
    fun getPlayer1BlockList(player1BlockList: BlockList): LiveData<BlockList>

    @Query("SELECT * FROM `game-entity` WHERE player_2_block_list LIKE :player2BlockList")
    fun getPlayer2BlockList(player2BlockList: BlockList): LiveData<BlockList>

    @Query("SELECT * FROM `game-entity` WHERE draw_game_block_list LIKE :drawGameBlockList")
    fun getDrawGameBlockList(drawGameBlockList: BlockList): LiveData<BlockList>

    @Query("SELECT * FROM `game-entity` WHERE is_game_over LIKE :isGameOver")
    fun getIsGameOver(isGameOver: Boolean): LiveData<Boolean>

    @Query("SELECT * FROM `game-entity` WHERE is_draw_game LIKE :isDrawGame")
    fun getIsDrawGame(isDrawGame: Boolean): LiveData<Boolean>

    @Query("SELECT * FROM `game-entity` WHERE is_game_in_progress LIKE :isGameInProgress")
    fun getIsGameInProgress(isGameInProgress: Boolean): LiveData<Boolean>

    @Query("SELECT * FROM `game-entity` WHERE winner LIKE :winner")
    fun getWinner(winner: Player): LiveData<Player>

}