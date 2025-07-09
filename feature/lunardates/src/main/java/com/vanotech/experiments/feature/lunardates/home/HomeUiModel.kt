package com.vanotech.experiments.feature.lunardates.home

import android.content.Context
import android.icu.util.Calendar
import android.icu.util.ChineseCalendar
import android.text.format.DateUtils
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.feature.lunardates.edit.EditRoute

internal data class HomeUiModel(
    private val event: Event,
) {
    val id = event.id

    private val lunarDate = "${event.month + 1} 月 ${event.dayOfMonth} 日"

    private fun gregorianDate(context: Context): String {
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

    @Composable
    fun Content(
        onClick: () -> Unit
    ) {
        Card(
            modifier = Modifier
                .fillMaxWidth()
                .clickable(onClick = onClick),
            shape = RoundedCornerShape(8.dp)
        ) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp)
            ) {
                Text(
                    text = event.title,
                    style = MaterialTheme.typography.titleMedium
                )

                Text(
                    text = lunarDate,
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = gregorianDate(LocalContext.current),
                    style = MaterialTheme.typography.bodyMedium
                )
            }
        }
    }

    @Composable
    fun Content(
        navController: NavController
    ) {
        Content {
            navController.navigate(route = EditRoute(event.id))
        }
    }
}

@Preview
@Composable
fun HomeItemPreview() {
    val event = Event.mockData(0)
    val item = HomeUiModel(event = event)
    item.Content { }
}