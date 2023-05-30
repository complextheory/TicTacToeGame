package com.r4ziel.tiktactoegame

import android.app.Application
import com.r4ziel.tiktactoegame.modules.dataBaseModule
import com.r4ziel.tiktactoegame.modules.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

/**
 * Created by Jarvis Charles on 5/26/23.
 */
class App: Application() {

    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidContext(this@App)
            modules(listOf(viewModelModule, dataBaseModule))
        }
    }
}