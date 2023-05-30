package com.r4ziel.tiktactoegame.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jarvis Charles on 5/30/23.
 */

@Entity(tableName = "turn-entity")
data class Turn (

    @PrimaryKey(autoGenerate = true)
    var turnNumber: Int,

    @ColumnInfo(name = "player")
    var player: Player?,
)

