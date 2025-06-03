package com.vanotech.experiments.core.utils.datastore

import android.content.Context
import androidx.datastore.preferences.preferencesDataStore

abstract class SimpleDataStore(private val context: Context, name: String) {

    private val Context.dataStore by preferencesDataStore(name)

    protected fun booleanPreference(
        keyName: String,
        default: Boolean
    ) = BooleanPreference(context.dataStore, keyName, default)

    protected fun intPreference(
        keyName: String,
        default: Int
    ) = IntPreference(context.dataStore, keyName, default)

    protected fun longPreference(
        keyName: String,
        default: Long
    ) = LongPreference(context.dataStore, keyName, default)

}