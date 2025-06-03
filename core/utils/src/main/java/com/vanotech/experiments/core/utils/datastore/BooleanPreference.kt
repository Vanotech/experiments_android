package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey

class BooleanPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: Boolean
) : Preference<Boolean>(
    dataStore,
    booleanPreferencesKey(keyName),
    default
)