package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import com.r4ziel.tiktactoegame.entities.Player
import com.r4ziel.tiktactoegame.entities.Turn

/**
 * Created by Jarvis Charles on 5/30/23.
 */
interface TurnDao {

    @Query("SELECT * FROM `turn-entity` WHERE player LIKE :turnNumber")
    fun findByTurnNumber(turnNumber: Int): LiveData<Int>

    @Query("SELECT * FROM `turn-entity` WHERE player LIKE :player")
    fun findByPlayer(player: Player): LiveData<Player>

    @Insert
    fun insertAll(vararg turn: Turn)

    @Delete
    fun delete(turn: Turn)

    @Update
    fun updateTurns(vararg turn: Turn)
}