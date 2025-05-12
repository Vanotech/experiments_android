package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

abstract class Preference<T>(
    private val dataStore: DataStore<Preferences>,
    private val preferenceKey: Preferences.Key<T>,
    default: T
) {

    val flow: Flow<T> = dataStore.data.map { preferences ->
        preferences[preferenceKey] ?: default
    }

    suspend fun set(value: T) {
        dataStore.edit { preferences ->
            preferences[preferenceKey] = value
        }
    }

    suspend fun remove() {
        dataStore.edit { preferences ->
            preferences.remove(preferenceKey)
        }
    }
}

