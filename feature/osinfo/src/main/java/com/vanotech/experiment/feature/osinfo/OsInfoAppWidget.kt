package com.vanotech.experiment.feature.osinfo

import android.content.Context
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceId
import androidx.glance.GlanceModifier
import androidx.glance.appwidget.GlanceAppWidget
import androidx.glance.appwidget.components.Scaffold
import androidx.glance.appwidget.lazy.GridCells
import androidx.glance.appwidget.lazy.LazyVerticalGrid
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Column
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.padding
import androidx.glance.text.FontWeight
import androidx.glance.text.Text
import androidx.glance.text.TextAlign
import androidx.glance.text.TextDefaults

internal class OsInfoAppWidget : GlanceAppWidget() {

    private val data = listOf(
        "API level" to OsInfo.getApiLevel(),
        "Codename" to OsInfo.getCodeName(),
        "Version" to OsInfo.getVersion()
    )

    override suspend fun provideGlance(context: Context, id: GlanceId) {
        provideContent {
            Content()
        }
    }

    @Composable
    private fun Content() {
        Scaffold {
            Column(
                modifier = GlanceModifier.fillMaxSize()
                    .padding(horizontal = 0.dp, vertical = 12.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                LazyVerticalGrid(gridCells = GridCells.Fixed(2)) {
                    val labelStyle = TextDefaults.defaultTextStyle.copy(
                        fontWeight = FontWeight.Medium,
                        textAlign = TextAlign.Start
                    )
                    val valueStyle = TextDefaults.defaultTextStyle.copy(
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.End
                    )
                    data.forEach {
                        item {
                            Text(
                                text = it.first,
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
}