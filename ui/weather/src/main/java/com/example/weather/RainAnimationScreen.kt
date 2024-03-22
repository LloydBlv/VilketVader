package com.example.weather

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import kotlin.time.Duration.Companion.seconds

@Composable
fun RainAnimationScreen() {
    val infiniteTransition = rememberInfiniteTransition()
    val raindropY = infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 1f,
        animationSpec = infiniteRepeatable(
            animation = tween(durationMillis = 1.5.seconds.inWholeMilliseconds.toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        )
    )

    val density = LocalDensity.current.density

    // Generate a list of raindrops with random starting points
    val raindrops = remember { List(100) { Raindrop(randomX(density)) } }

    Canvas(modifier = Modifier.fillMaxSize()) {
        val canvasHeight = size.height

        raindrops.forEach { raindrop ->
            val y = raindropY.value * canvasHeight + raindrop.offsetY
            // When a drop reaches the bottom of the screen, it restarts from the top
            if (y - raindrop.length > canvasHeight) {
                raindrop.offsetY = -raindrop.length
            }

            drawLine(
                color = Color.Cyan,
                start = Offset(raindrop.x, y - raindrop.length),
                end = Offset(raindrop.x, y),
                strokeWidth = raindrop.strokeWidth
            )
        }
    }
}

private fun randomX(density: Float) = (0..1000).random() * density

data class Raindrop(var x: Float, val length: Float = 10f, val strokeWidth: Float = 3f) {
    var offsetY: Float = -(0..1000).random().toFloat()
}
