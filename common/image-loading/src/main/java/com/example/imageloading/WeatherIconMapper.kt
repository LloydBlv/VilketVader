package com.example.imageloading

import coil.map.Mapper
import coil.request.Options
import com.example.domain.Icon

class WeatherIconMapper : Mapper<Icon, String> {
    override fun map(data: Icon, options: Options): String? {
        return "https://openweathermap.org/img/wn/${data.type}@2x.png"
    }
}