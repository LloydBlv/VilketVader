package com.example.widget

import android.os.Build
import androidx.compose.runtime.Composable
import androidx.compose.ui.unit.dp
import androidx.glance.GlanceModifier
import androidx.glance.GlanceTheme
import androidx.glance.appwidget.appWidgetBackground
import androidx.glance.appwidget.cornerRadius
import androidx.glance.background
import androidx.glance.layout.fillMaxSize
import androidx.glance.layout.padding

@Composable
fun GlanceModifier.appWidgetBackgroundModifier(): GlanceModifier {
    return this.fillMaxSize()
        .padding(16.dp)
        .appWidgetBackground()
        .background(GlanceTheme.colors.background)
        .appWidgetBackgroundCornerRadius()
}

fun GlanceModifier.appWidgetInnerCornerRadius(): GlanceModifier {
    if (Build.VERSION.SDK_INT >= 31) {
        return cornerRadius(android.R.dimen.system_app_widget_inner_radius)
    }
    return cornerRadius(8.dp)
}

fun GlanceModifier.appWidgetBackgroundCornerRadius(): GlanceModifier {
    if (Build.VERSION.SDK_INT >= 31) {
        cornerRadius(android.R.dimen.system_app_widget_background_radius)
    }
    return cornerRadius(16.dp)
}
