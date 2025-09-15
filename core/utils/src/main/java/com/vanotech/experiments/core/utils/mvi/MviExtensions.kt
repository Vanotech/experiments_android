package com.vanotech.experiments.core.utils.mvi

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope

fun <State, Intent> ViewModel.StateReducerFlow(
    initialState: State,
    reduceState: (State, Intent) -> State,
): StateReducerFlow<State, Intent> = StateReducerFlowImpl(initialState, reduceState, viewModelScope)
