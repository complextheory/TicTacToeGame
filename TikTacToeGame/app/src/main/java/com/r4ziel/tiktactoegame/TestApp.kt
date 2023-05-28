package com.r4ziel.tiktactoegame

import android.app.Application
import org.koin.core.context.startKoin
import org.koin.core.context.stopKoin

/**
 * Created by Jarvis Charles on 5/28/23.
 */
class TestApp: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin{

        }
    }

    override fun onTerminate() {
        stopKoin()
        super.onTerminate()
    }
}