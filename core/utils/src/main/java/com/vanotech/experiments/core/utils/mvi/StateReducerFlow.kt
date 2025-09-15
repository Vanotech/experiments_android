package com.vanotech.experiments.core.utils.mvi

import kotlinx.coroutines.flow.StateFlow

interface StateReducerFlow<State, Intent> : StateFlow<State> {
    fun handleIntent(intent: Intent)
}

