package com.vanotech.experiments.core.utils.mvi

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.FlowCollector
import kotlinx.coroutines.flow.SharingStarted.Companion.Eagerly
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.runningFold
import kotlinx.coroutines.flow.stateIn

internal class StateReducerFlowImpl<State, Intent>(
    initialState: State,
    reduceState: (State, Intent) -> State,
    coroutineScope: CoroutineScope
) : StateReducerFlow<State, Intent> {

    private val intents = Channel<Intent>()

    private val stateFlow = intents
        .receiveAsFlow()
        .runningFold(initialState, reduceState)
        .stateIn(coroutineScope, Eagerly, initialState)

    override val replayCache get() = stateFlow.replayCache

    override val value get() = stateFlow.value

    override suspend fun collect(collector: FlowCollector<State>): Nothing {
        stateFlow.collect(collector)
    }

    override fun handleIntent(intent: Intent) {
        intents.trySend(intent)
    }
}