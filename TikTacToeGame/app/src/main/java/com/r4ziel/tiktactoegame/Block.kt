package com.r4ziel.tiktactoegame

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Jarvis Charles on 5/26/23.
 */

@Parcelize
data class Block(

    var xOrO: String?,
    val id: Int,
    var isClicked: Boolean,
    var player: Int
): Parcelable
