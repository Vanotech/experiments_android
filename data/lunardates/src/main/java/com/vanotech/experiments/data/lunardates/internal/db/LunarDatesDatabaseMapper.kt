package com.vanotech.experiments.data.lunardates.internal.db

import com.vanotech.experiments.data.lunardates.Event

internal fun Event.toEventEntity() = EventEntity(
    id = id,
    title = title,
    dayOfMonth = dayOfMonth,
    month = month
)

internal fun EventEntity.toEvent() = Event(
    id = id,
    title = title,
    dayOfMonth = dayOfMonth,
    month = month
)
