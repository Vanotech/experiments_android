package com.vanotech.experiments.core.utils.mvi

import kotlinx.coroutines.ExperimentalForInheritanceCoroutinesApi
import kotlinx.coroutines.flow.StateFlow

@OptIn(ExperimentalForInheritanceCoroutinesApi::class)
interface StateReducerFlow<State, Intent> : StateFlow<State> {
    fun handleIntent(intent: Intent)
}

