package com.vanotech.experiments.core.utils.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.stringPreferencesKey

class StringPreference(
    dataStore: DataStore<Preferences>,
    keyName: String,
    default: String
) : Preference<String>(
    dataStore,
    stringPreferencesKey(keyName),
    default
)