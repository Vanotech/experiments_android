package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.intPreferencesKey

class IntPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: Int
) : Preference<Int>(
    dataStore,
    intPreferencesKey(keyName),
    default
)