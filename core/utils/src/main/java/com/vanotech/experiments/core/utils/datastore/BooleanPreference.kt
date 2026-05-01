package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

internal class BooleanPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: Boolean
) : SimplePreference<Boolean>(
    dataStore,
    booleanPreferencesKey(keyName),
    default
)