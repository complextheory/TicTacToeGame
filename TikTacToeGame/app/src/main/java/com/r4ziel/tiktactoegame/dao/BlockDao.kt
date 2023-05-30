package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import com.r4ziel.tiktactoegame.entities.Block

/**
 * Created by Jarvis Charles on 5/29/23.
 */
interface BlockDao {

    @Query("SELECT * FROM `block-entity`")
    fun getAll(): LiveData<List<Block>>

    @Query("SELECT * FROM `block-entity` WHERE is_clicked LIKE :isClicked")
    fun findByIsClicked(isClicked: Boolean): LiveData<Block>

    @Query("SELECT * FROM `block-entity` WHERE is_clicked LIKE :playerNumber")
    fun findByIsPlayerNumber(playerNumber: Int): LiveData<Block>

    @Insert
    fun insertAll(vararg todo: Block)

    @Delete
    fun delete(block: Block)

    @Update
    fun updateBlocks(vararg todos: Block)
}