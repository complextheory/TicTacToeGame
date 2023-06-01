package com.r4ziel.tiktactoegame.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jarvis Charles on 5/29/23.
 */
@Entity(tableName = "player-entity")
data class Player(

    @PrimaryKey
    val id: Int,

    @ColumnInfo(name = "block_ids")
    var blockIds: List<Int>,
)
