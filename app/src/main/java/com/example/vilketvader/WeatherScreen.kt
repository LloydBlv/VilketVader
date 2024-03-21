package com.example.vilketvader

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Compress
import androidx.compose.material.icons.filled.ViewCozy
import androidx.compose.material.icons.filled.WaterDrop
import androidx.compose.material.icons.filled.WindPower
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.runtime.getValue
import androidx.compose.runtime.produceState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.example.data.WeatherApiClientDefault
import com.example.data.repositories.WeatherRepositoryDefault
import com.example.domain.Weather
import com.example.vilketvader.ui.theme.VilketVaderTheme


data class WeatherState(
    val isLoading: Boolean = false,
    val weather: Weather? = null
)

@Composable
private fun getWeather(): State<WeatherState> {
    return produceState(initialValue = WeatherState(isLoading = true)) {
        val client = WeatherApiClientDefault()
        val weather = WeatherRepositoryDefault(client).getWeather("Stockholm")
        value = WeatherState(weather = weather)
    }

}

@Composable
fun WeatherScreenUi(modifier: Modifier = Modifier) {
    val state: WeatherState by getWeather()
    Scaffold(modifier = modifier.background(Color.Blue)) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {
            Spacer(modifier = Modifier.height(20.dp))

            Row {
                Column {
                    Text(
                        text = "${state.weather?.temperature?.current}°",
                        style = MaterialTheme.typography.displayLarge
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = "${state.weather?.conditions?.firstOrNull()?.name}",
                        style = MaterialTheme.typography.titleLarge
                    )

                }
                AsyncImage(
                    modifier = Modifier.size(120.dp),
                    model = "https://openweathermap.org/img/wn/${state.weather?.icon}@2x.png",
                    contentDescription = null
                )
            }

            Spacer(modifier = Modifier.height(40.dp))

            Text(
                text = "${state.weather?.temperature?.max}° / ${state.weather?.temperature?.min}° feels like ${state.weather?.temperature?.feelsLike}°",
                style = MaterialTheme.typography.titleLarge
            )
            Text(
                text = "${state.weather?.timestamp} Wednesday, 15:40",
                color = Color.Black.copy(alpha = 0.6f),
                style = MaterialTheme.typography.titleSmall
            )
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                WeatherInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.WindPower,
                    fieldName = "Wind",
                    fieldValue = "${state.weather?.wind?.speed} m/s"
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .weight(0.1f)
                )
                WeatherInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.WaterDrop,
                    fieldName = "Humidity",
                    fieldValue = "${state.weather?.humidity}%"
                )
            }
            Spacer(modifier = Modifier.height(20.dp))
            Row(modifier = Modifier.fillMaxWidth()) {
                WeatherInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.Compress,
                    fieldName = "Pressure",
                    fieldValue = "${state.weather?.pressure} hg"
                )
                Spacer(
                    modifier = Modifier
                        .height(10.dp)
                        .weight(0.1f)
                )
                WeatherInfoCard(
                    modifier = Modifier.weight(1f),
                    icon = Icons.Default.ViewCozy,
                    fieldName = "Visibility",
                    fieldValue = "${state.weather?.visibility}m"
                )
            }
        }
    }

}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    fieldName: String,
    fieldValue: String
) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(10.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null)
                Spacer(modifier = Modifier.width(10.dp))
                Text(text = fieldName, style = MaterialTheme.typography.titleSmall)
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(text = fieldValue, style = MaterialTheme.typography.titleLarge)
        }
    }
}

@Preview
@Composable
private fun WeatherScreenUiPreview() {
    VilketVaderTheme {
        WeatherScreenUi()
    }
}