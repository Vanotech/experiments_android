package com.vanotech.experiments.core.utils

import androidx.room.concurrent.AtomicInt

data class VersionedValue<T>(
    val value: T,
    val version: Int
) {
    class Factory<T> {
        private val version = AtomicInt();

        fun newInstance(value: T) = VersionedValue(
            value = value,
            version = version.getAndIncrement()
        )
    }
}