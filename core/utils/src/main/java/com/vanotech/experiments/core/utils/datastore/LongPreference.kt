package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.longPreferencesKey

internal class LongPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: Long
) : SimplePreference<Long>(
    dataStore,
    longPreferencesKey(keyName),
    default
)