package com.vanotech.experiment.feature.osinfo

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.LocalContext
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextStyle

internal class OsInfoAppWidget : GlanceAppWidget() {

    private val dataSet = listOf(
        R.string.label_api_level to OsInfo.getApiLevel(),
        R.string.label_codename to OsInfo.getCodeName(),
        R.string.label_version to OsInfo.getVersion()
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            GlanceTheme {
                Content()
            }
        }
    }

    @Composable
    private fun Content() {
        Scaffold {
            LazyVerticalGrid(
                gridCells = GridCells.Fixed(2),
                modifier = GlanceModifier.padding(horizontal = 0.dp, vertical = 12.dp)
            ) {
                val labelStyle = TextStyle(
                    fontWeight = FontWeight.Medium,
                    textAlign = TextAlign.Start
                )
                val valueStyle = TextStyle(
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.End
                )
                dataSet.forEach {
                    item {
                        Text(
                            text = LocalContext.current.getString(it.first),
                            modifier = GlanceModifier.fillMaxWidth(),
                            style = labelStyle
                        )
                    }
                    item {
                        Text(
                            text = it.second,
                            modifier = GlanceModifier.fillMaxWidth(),
                            style = valueStyle
                        )
                    }
                }
            }
        }
    }
}