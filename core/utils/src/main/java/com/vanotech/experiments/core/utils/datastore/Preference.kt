package com.vanotech.experiments.core.utils.datastore

import kotlinx.coroutines.flow.Flow

interface Preference<T> {

    val flow: Flow<T>

    suspend fun get(): T

    suspend fun set(value: T)

    suspend fun remove()
}

