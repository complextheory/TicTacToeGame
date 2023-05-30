package com.r4ziel.tiktactoegame.dao

import android.arch.persistence.room.Delete
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import android.arch.persistence.room.Update
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.r4ziel.tiktactoegame.entities.Block
import com.r4ziel.tiktactoegame.entities.BlockList

/**
 * Created by Jarvis Charles on 5/30/23.
 */
interface BlockListDao {

    @Query("SELECT * FROM `block-list-entity`")
    fun getAll(): MutableLiveData<List<List<Block>>>

    @Query("SELECT * FROM `block-list-entity` WHERE block_list_name LIKE :name")
    fun findByName(name: String): MutableLiveData<Block>

    @Insert
    fun insertAll(vararg blockList: BlockList)

    @Delete
    fun delete(blockList: BlockList)

    @Update
    fun updateBlockLists(vararg blockList: BlockList)
}