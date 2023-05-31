package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.r4ziel.tiktactoegame.entities.Block

/**
 * Created by Jarvis Charles on 5/29/23.
 */
interface BlockDao {

    @Query("SELECT * FROM `block-entity`")
    fun getAll(): MutableLiveData<List<Block>>

    @Query("SELECT * FROM `block-entity` WHERE is_clicked LIKE :isClicked")
    fun findByIsClicked(isClicked: Boolean): MutableLiveData<Block>

    @Query("SELECT * FROM `block-entity` WHERE is_clicked LIKE :playerNumber")
    fun findByIsPlayerNumber(playerNumber: Int): MutableLiveData<Block>

    @Insert
    fun insertAll(vararg block: Block)

    @Delete
    fun delete(block: Block)

    @Update
    fun updateBlockList(vararg blockList: MutableList<Block>)
}