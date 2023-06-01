package com.r4ziel.tiktactoegame.modules

import android.arch.persistence.room.Room
import com.r4ziel.tiktactoegame.App
import com.r4ziel.tiktactoegame.database.GameDatabase
import org.koin.dsl.module

/**
 * Created by Jarvis Charles on 5/30/23.
 */

val dataBaseModule = module {
    single {
        Room.databaseBuilder(
            App(),
            GameDatabase::class.java,
            "GAME_DB"
        ).build()
    }
    single {
        val database = get<GameDatabase>()
        database.BlockDao()
    }

    single {
        val database = get<GameDatabase>()
        database.PlayerDao()
    }
}