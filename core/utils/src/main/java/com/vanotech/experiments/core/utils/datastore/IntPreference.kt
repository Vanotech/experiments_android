package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

internal class IntPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: Int
) : SimplePreference<Int>(
    dataStore,
    intPreferencesKey(keyName),
    default
)