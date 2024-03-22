package com.example.weather

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxScope
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.time.Duration
import java.time.LocalTime
import kotlin.math.PI
import kotlin.math.cos

@Composable
fun SunriseSunsetUI() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Canvas(modifier = Modifier.fillMaxWidth().aspectRatio(1.0f)) {
            val canvasWidth = size.width
            val canvasHeight = size.height / 2  // Half the height because we only need the top semicircle

            // The stroke for the path should be solid with the right color and width
            val stroke = Stroke(width = 4.dp.toPx(), pathEffect = PathEffect.cornerPathEffect(4.dp.toPx()))

            // Start and end points of the arc
            val startPoint = Offset(0f, canvasHeight)
            val endPoint = Offset(canvasWidth, canvasHeight)

            // Control points for the Bezier curve, adjusted to create a semicircle
            val controlOffset = canvasWidth * 0.5f  // Adjust this value as needed to perfect the curve
            val controlPoint1 = Offset(controlOffset, 0f)
            val controlPoint2 = Offset(canvasWidth - controlOffset, 0f)

            val path = Path().apply {
                moveTo(startPoint.x, startPoint.y)
                cubicTo(
                    x1 = controlPoint1.x, y1 = controlPoint1.y,
                    x2 = controlPoint2.x, y2 = controlPoint2.y,
                    x3 = endPoint.x, y3 = endPoint.y
                )
            }

            drawPath(path = path, brush = SolidColor(Color.Yellow), style = stroke)

            // Calculate the sun's position to place it on the arc
            val sunRadius = 24.dp.toPx()
            val sunPosition = Offset(x = canvasWidth / 2, y = controlOffset - sunRadius)

            drawCircle(
                brush = SolidColor(Color.Yellow),
                radius = sunRadius,
                center = sunPosition
            )
        }
        Spacer(Modifier.height(16.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(horizontalAlignment = Alignment.Start) {
                Text(text = "Soluppgång", fontSize = 16.sp, color = Color.Black)
                Text(text = "05:48", fontSize = 16.sp, color = Color.Black)
            }
            Column(horizontalAlignment = Alignment.End) {
                Text(text = "Solnedgång", fontSize = 16.sp, color = Color.Black)
                Text(text = "18:02", fontSize = 16.sp, color = Color.Black)
            }
        }
    }
}
@Composable
fun SunPathUI(sunriseTime: LocalTime, sunsetTime: LocalTime, currentTime: LocalTime) {
    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(32.dp)
    ) {
        val sunPosition = calculateSunPosition(sunriseTime, sunsetTime, currentTime)
        SunPath()
        SunIndicator(sunPosition)
        SunTimeLabel(time = sunriseTime.toString(), isSunrise = true)
        SunTimeLabel(time = sunsetTime.toString(), isSunrise = false)
    }
}

@Composable
fun SunPath() {
    Canvas(modifier = Modifier
        .fillMaxWidth()
        .aspectRatio(16/9f)
    ) {
        val path = Path().apply {
            arcTo(
                rect = Rect(0f, 0f, size.width, size.height * 1.5f),
                startAngleDegrees = 180f,
                sweepAngleDegrees = 180f,
                forceMoveTo = false
            )
        }
        drawPath(path = path, brush = SolidColor(Color.LightGray), style = Stroke(width = 4.dp.toPx()))
    }
}

@Composable
fun SunIndicator(position: Float) {
    // Assuming the sun's path is a semicircle from sunrise (0) to sunset (1)
    val arcRadius = 100.dp.value // This should be the same as the height of the SunPath Canvas
    val sunDiameter = 30.dp

    Canvas(modifier = Modifier
        .fillMaxWidth()
        .height((arcRadius * 2).dp)
    ) {
        val x = (size.width * position)
        val angle = PI * position
        val y = arcRadius - (arcRadius * cos(angle)).toFloat()

        drawCircle(
            color = Color.Yellow,
            radius = sunDiameter.toPx() / 2,
            center = androidx.compose.ui.geometry.Offset(x, y)
        )
    }
}

@Composable
fun BoxScope.SunTimeLabel(time: String, isSunrise: Boolean) {
    Column(
        modifier = Modifier
            .align(if (isSunrise) Alignment.TopStart else Alignment.TopEnd)
            .padding(top = 220.dp)
    ) {
        Text(text = if (isSunrise) "U" else "N", fontSize = 12.sp)
        Spacer(modifier = Modifier.height(4.dp))
        Text(text = "", fontSize = 16.sp, color = Color.DarkGray)
    }
}

fun calculateSunPosition(sunrise: LocalTime, sunset: LocalTime, current: LocalTime): Float {
    val dayDuration = Duration.between(sunrise, sunset).toMinutes().toFloat()
    val timeSinceSunrise = Duration.between(sunrise, current).toMinutes().toFloat()
    return (timeSinceSunrise / dayDuration).coerceIn(0f, 1f)
}