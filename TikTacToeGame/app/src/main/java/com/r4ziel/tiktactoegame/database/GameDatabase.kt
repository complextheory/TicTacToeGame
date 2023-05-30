package com.r4ziel.tiktactoegame.database

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.r4ziel.tiktactoegame.dao.*
import com.r4ziel.tiktactoegame.entities.*

/**
 * Created by Jarvis Charles on 5/30/23.
 */
@Database(
    entities = [Game::class, Turn::class, BlockList::class, Block::class, Player::class],
    version = 1
)
abstract class GameDatabase: RoomDatabase() {

    abstract fun GameDao(): GameDao
    abstract fun TurnDao(): TurnDao
    abstract fun BlockListDao(): BlockList
    abstract fun BlockDao(): BlockDao
    abstract fun PlayerDao(): PlayerDao

    companion object {
        @Volatile private var instance: GameDatabase? = null
        private val LOCK = Any()

        operator fun invoke(context: Context)= instance ?: synchronized(LOCK){
            instance ?: buildDatabase(context).also { instance = it}
        }

        private fun buildDatabase(context: Context) = Room.databaseBuilder(context,
            GameDatabase::class.java, "todo-list.db")
            .build()
    }
}