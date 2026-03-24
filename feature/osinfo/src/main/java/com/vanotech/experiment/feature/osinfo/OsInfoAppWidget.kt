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
import androidx.glance.appwidget.lazy.LazyColumn
import androidx.glance.appwidget.lazy.items
import androidx.glance.appwidget.provideContent
import androidx.glance.layout.Alignment
import androidx.glance.layout.Row
import androidx.glance.layout.Spacer
import androidx.glance.layout.fillMaxWidth
import androidx.glance.layout.height
import androidx.glance.layout.padding
import androidx.glance.layout.width
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
            LazyColumn {
                item {
                    Spacer(modifier = GlanceModifier.height(12.dp))
                }
                items(dataSet) { item ->
                    OsInfoCard(
                        item = item,
                        modifier = GlanceModifier.fillMaxWidth()
                    )
                }
                item {
                    Spacer(modifier = GlanceModifier.height(12.dp))
                }
            }
        }
    }

    @Composable
    private fun OsInfoCard(
        item: Pair<Int, String>,
        modifier: GlanceModifier = GlanceModifier
    ) {
        val context = LocalContext.current

        val containerColor = GlanceTheme.colors.surface
        val contentColor = GlanceTheme.colors.onSurface
        val labelStyle = TextStyle(
            color = contentColor,
            textAlign = TextAlign.Start
        )
        val valueStyle = TextStyle(
            color = contentColor,
            fontWeight = FontWeight.Bold,
            textAlign = TextAlign.End
        )

        Row(
            modifier = modifier.padding(horizontal = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                text = context.getString(item.first),
                modifier = GlanceModifier.defaultWeight(),
                style = labelStyle
            )
            Spacer(modifier = GlanceModifier.width(8.dp))
            Text(
                text = item.second,
                modifier = GlanceModifier.defaultWeight(),
                style = valueStyle
            )
        }
    }
}