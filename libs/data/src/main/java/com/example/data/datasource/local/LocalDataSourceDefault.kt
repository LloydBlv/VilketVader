package com.example.data.datasource.local

import com.example.domain.Weather
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import timber.log.Timber
import javax.inject.Inject


interface LocalDataSource {
    suspend fun getWeather(id: Int): Weather?
    fun observeWeather(id: Int): Flow<Weather?>

    suspend fun updateWeather(weather: Weather)
}

class LocalDataSourceDefault @Inject constructor(
    private val weatherDao: WeatherDao
) : LocalDataSource {
    override suspend fun getWeather(id: Int): Weather? {
        val weatherAndLocation = weatherDao.getWeather(locationId = id)
        Timber.i("getWeather[%s]=[%s]", id, weatherAndLocation)
        weatherDao.getOnlyAllWeathers().forEachIndexed { index, weather ->
            Timber.tag("getWeather")
                .e("allWeathers[%s]=%s", index, weather)
        }
        return weatherAndLocation?.toDomain()
    }
    override suspend fun updateWeather(weather: Weather) {
        val weatherEntity = weather.toEntity()
        Timber.e("going to insert=%s", weatherEntity)
        weatherDao.getOnlyAllWeathers().forEach {
            Timber.tag("updateWeather").e("allWeathers=%s", it)
        }
        weatherDao.insertWeather(weatherEntity)
        weatherDao.getAllWeathers().forEach {
            Timber.tag("after-insert-updateWeather").e("allWeathers=%s", it)
        }
    }

    override fun observeWeather(id: Int): Flow<Weather?> {
        return weatherDao.observeWeather(id)
            .map { it?.toDomain() }
    }
}