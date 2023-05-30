package com.r4ziel.tiktactoegame.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jarvis Charles on 5/30/23.
 */
@Entity(tableName = "block-list-entity")
data class BlockList(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @ColumnInfo(name = "block_list")
    var blockList: List<Block>?,

    @ColumnInfo(name = "block_list_name")
    var blockListName: String
)