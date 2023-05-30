package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.r4ziel.tiktactoegame.entities.Player

/**
 * Created by Jarvis Charles on 5/29/23.
 */
interface PlayerDao {

    @Query("SELECT * FROM `player-entity`")
    fun getAll(): MutableLiveData<List<Player>>

    @Query("SELECT * FROM  `player-entity` WHERE id LIKE :playerId")
    fun findByPlayerId(playerId: Int): MutableLiveData<Player>

    @Query("SELECT * FROM `player-entity` WHERE player_name LIKE :playerName")
    fun findByPlayerName(playerName: String): MutableLiveData<Player>

    @Insert
    fun insertAll(vararg player: Player)

    @Delete
    fun delete(player: Player)

    @Update
    fun updatePlayers(vararg player: Player)
}