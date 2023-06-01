package com.r4ziel.tiktactoegame.entities

import android.arch.persistence.room.ColumnInfo
import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey

/**
 * Created by Jarvis Charles on 5/26/23.
 */

@Entity(tableName = "block-entity")
data class Block(

    @PrimaryKey(autoGenerate = true)
    val id: Int,

    @PrimaryKey
    var playerNumber: Int,

    @ColumnInfo(name = "xOrO")
    var xOrO: String?,

    @ColumnInfo(name = "is_clicked")
    var isClicked: Boolean


)
