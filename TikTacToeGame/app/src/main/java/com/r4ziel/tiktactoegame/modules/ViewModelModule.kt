package com.r4ziel.tiktactoegame.modules

import com.r4ziel.tiktactoegame.GameFragmentViewModel
import org.koin.dsl.module

/**
 * Created by Jarvis Charles on 5/26/23.
 */

val viewModelModule = module {
    single { GameFragmentViewModel(get(), get()) }
}