package com.example.vilketvader

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
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
import androidx.compose.material3.CardDefaults
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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
private fun getWeather(location: UiLocation): State<WeatherState> {
    return produceState(initialValue = WeatherState(isLoading = true), location) {
        val client = WeatherApiClientDefault()
        val weather = WeatherRepositoryDefault(client).getWeather(location.cityName)
        value = WeatherState(weather = weather)
    }

}

@Composable
fun WeatherScreenUi(
    modifier: Modifier = Modifier,
    location: UiLocation,
    paddingValues: PaddingValues = PaddingValues(0.dp)
) {

    val state: WeatherState by getWeather(location)
    Scaffold(
        containerColor = Color.Transparent,
        modifier = modifier.padding(paddingValues),
    ) {
        Column(
            modifier = Modifier
                .padding(it)
                .padding(8.dp)
        ) {
            CurrentTemperatureCard(state)
            Spacer(modifier = Modifier.height(40.dp))
            MinMaxTemperature(state)
            TimeStampText(state)
            Spacer(modifier = Modifier.height(20.dp))
            WindAndHumidityCard(state)
            Spacer(modifier = Modifier.height(20.dp))
            PressureAndVisibilityCards(state)
        }
    }

}

@Composable
private fun PressureAndVisibilityCards(state: WeatherState) {
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

@Composable
private fun WindAndHumidityCard(state: WeatherState) {
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
}

@Composable
private fun TimeStampText(state: WeatherState) {
    Text(
        text = "${state.weather?.timestamp} Wednesday, 15:40",
        color = Color.White.copy(alpha = 0.6f),
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun MinMaxTemperature(state: WeatherState) {
    Text(
        text = "${state.weather?.temperature?.max}° / ${state.weather?.temperature?.min}° feels like ${state.weather?.temperature?.feelsLike}°",
        style = MaterialTheme.typography.titleLarge
    )
}

@Composable
private fun CurrentTemperatureCard(state: WeatherState) {
    Row(modifier = Modifier.padding(8.dp)) {
        Column {
            Text(
                text = "${state.weather?.temperature?.current}°",
                style = MaterialTheme.typography.displayLarge.copy(fontSize = 67.sp)
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
}

@Composable
fun WeatherInfoCard(
    modifier: Modifier = Modifier,
    icon: ImageVector,
    fieldName: String,
    fieldValue: String
) {
    Card(
        modifier = modifier,
        colors = CardDefaults.elevatedCardColors(
            containerColor = Color.DarkGray.copy(alpha = 0.15f)
        )
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(15.dp)
        ) {
            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(icon, contentDescription = null, tint = Color.White.copy(alpha = 0.6f))
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = fieldName, style = MaterialTheme.typography.titleSmall,
                    color = Color.White.copy(alpha = 0.6f)
                )
            }
            Spacer(modifier = Modifier.height(10.dp))
            Text(
                text = fieldValue, style = MaterialTheme.typography.headlineSmall,
                fontWeight = FontWeight.Bold
            )
        }
    }
}

@Preview
@Composable
private fun WeatherScreenUiPreview() {
    VilketVaderTheme {
        WeatherScreenUi(location = UiLocation("Stockholm"))
    }
}