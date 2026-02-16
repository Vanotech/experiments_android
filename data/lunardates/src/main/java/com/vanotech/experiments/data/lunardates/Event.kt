package com.vanotech.experiments.data.lunardates


data class Event(
    val id: Int,
    val title: String,
    val dayOfMonth: Int,
    val month: Int
) {
    companion object {
        fun mockData(index: Int): Event {
            return Event(
                id = index,
                title = "Title $index",
                dayOfMonth = index,
                month = index
            )
        }
    }
}