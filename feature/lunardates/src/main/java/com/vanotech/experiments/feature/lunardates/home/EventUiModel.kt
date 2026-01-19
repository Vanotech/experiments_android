package com.vanotech.experiments.feature.lunardates.home

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.ChineseCalendar
import android.text.format.DateUtils
import androidx.compose.runtime.Immutable
import androidx.navigation.NavController
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.feature.lunardates.LunarDatesNavGraph

@Immutable
internal data class EventUiModel(
    private val event: Event
) {
    val id = event.id

    val title = event.title

    val lunarDate = "${event.month + 1} 月 ${event.dayOfMonth} 日"

    fun gregorianDate(context: Context): String {
        val chineseCalendar = ChineseCalendar().apply {
            set(Calendar.MONTH, event.month)
            set(Calendar.DAY_OF_MONTH, event.dayOfMonth)
            if (timeInMillis < System.currentTimeMillis()) {
                add(Calendar.YEAR, 1)
            }
        }
        return DateUtils.formatDateTime(
            context,
            chineseCalendar.timeInMillis,
            DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
        )
    }

    fun navigate(navController: NavController) {
        LunarDatesNavGraph.navigateToEdit(navController, event.id)
    }
}