package com.vanotech.experiments.feature.lunardates.home

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
import androidx.navigation.compose.rememberNavController
import com.vanotech.experiments.data.lunardates.Event
import com.vanotech.experiments.feature.lunardates.edit.EditRoute

@Composable
internal fun HomeItem(
    event: Event,
    navController: NavController
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable {
                navController.navigate(route = EditRoute(event.id))
            },
        shape = RoundedCornerShape(8.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
        ) {
            val chineseCalendar = ChineseCalendar().apply {
                set(Calendar.MONTH, event.month)
                set(Calendar.DAY_OF_MONTH, event.dayOfMonth)
                if (timeInMillis < System.currentTimeMillis()) {
                    add(Calendar.YEAR, 1)
                }
            }

            val lunarDate = "${event.month + 1} 月 ${event.dayOfMonth} 日"
            val gregorianDate = DateUtils.formatDateTime(
                LocalContext.current,
                chineseCalendar.timeInMillis,
                DateUtils.FORMAT_SHOW_DATE or DateUtils.FORMAT_SHOW_YEAR
            )
            Text(
                text = event.title,
                style = MaterialTheme.typography.titleMedium
            )
            Text(
                text = lunarDate,
                style = MaterialTheme.typography.bodyMedium
            )
            Text(
                text = gregorianDate,
                style = MaterialTheme.typography.bodyMedium
            )
        }
    }
}

@Preview
@Composable
fun EventItemPreview() {
    val event = Event.mockData(0)
    HomeItem(
        event = event,
        navController = rememberNavController()
    )
}