package com.example.widget

import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.background
import androidx.glance.layout.Alignment
import androidx.glance.layout.Box
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding

@Composable
fun AppWidgetBox(
    modifier: GlanceModifier = GlanceModifier,
    contentAlignment: Alignment = Alignment.TopStart,
    content: @Composable () -> Unit,
) {
    Box(
        modifier = GlanceModifier.fillMaxSize()
            .padding(16.dp)
            .appWidgetBackground()
            .background(GlanceTheme.colors.background)
            .appWidgetBackgroundCornerRadius().then(modifier),
        contentAlignment = contentAlignment,
        content = content,
    )
}
