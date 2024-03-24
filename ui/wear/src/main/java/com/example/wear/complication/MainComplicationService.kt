package com.example.wear.complication

import android.app.PendingIntent
import android.content.Intent
import android.graphics.drawable.Icon
import androidx.wear.watchface.complications.data.ComplicationData
import androidx.wear.watchface.complications.data.ComplicationType
import androidx.wear.watchface.complications.data.PlainComplicationText
import androidx.wear.watchface.complications.data.ShortTextComplicationData
import androidx.wear.watchface.complications.data.SmallImage
import androidx.wear.watchface.complications.data.SmallImageType
import androidx.wear.watchface.complications.datasource.ComplicationRequest
import androidx.wear.watchface.complications.datasource.SuspendingComplicationDataSourceService
import com.example.domain.GetSelectedWeatherUseCase
import com.example.domain.Weather
import com.example.wear.R
import com.example.wear.di.getSelectedWeatherUseCase
import com.example.wear.presentation.MainWearActivity

/**
 * Skeleton for complication data source that returns short text.
 */
class MainComplicationService : SuspendingComplicationDataSourceService() {

    override fun getPreviewData(type: ComplicationType): ComplicationData? {
        if (type != ComplicationType.SHORT_TEXT) {
            return null
        }
        return createComplicationData("Mon", "Monday")
    }



    override suspend fun onComplicationRequest(request: ComplicationRequest): ComplicationData {
        val result: Result<Weather> = getSelectedWeatherUseCase(applicationContext).invoke(GetSelectedWeatherUseCase.Params())
        val weather = result.getOrThrow()
        return createComplicationData(text = "${weather.temperature.current}°", contentDescription = "Temperature")
    }

    private fun createComplicationData(
        text: String,
        contentDescription: String
    ): ShortTextComplicationData {
        val intent = Intent(applicationContext, MainWearActivity::class.java)
        val pendingIntent = PendingIntent.getActivity(
            applicationContext,
            0,
            intent,
            PendingIntent.FLAG_IMMUTABLE or PendingIntent.FLAG_UPDATE_CURRENT
        )
        return ShortTextComplicationData.Builder(
            text = PlainComplicationText.Builder(text).build(),
            contentDescription = PlainComplicationText.Builder(contentDescription).build()
        ).setSmallImage(SmallImage.Builder(
            image = Icon.createWithResource(applicationContext, R.mipmap.ic_launcher),
            type = SmallImageType.PHOTO
        ).build())
            .setTapAction(pendingIntent)
            .build()
    }
}