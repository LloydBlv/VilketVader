package com.example.vilketvader

import androidx.compose.ui.test.assertIsDisplayed
import androidx.compose.ui.test.assertIsNotDisplayed
import androidx.compose.ui.test.isDisplayed
import androidx.compose.ui.test.onNodeWithContentDescription
import androidx.compose.ui.test.onNodeWithTag
import androidx.compose.ui.test.onNodeWithText
import androidx.compose.ui.test.performClick
import androidx.test.platform.app.InstrumentationRegistry
import com.example.domain.Weather
import com.example.weather.R
import javax.inject.Inject

class MainActivityRobot @Inject constructor() {
    context (RobotTestRule)
    fun assertRetryButtonIdDisplayed() {
        composeTestRule.onNodeWithText("Retry").assertIsDisplayed()
    }

    context(RobotTestRule)
    fun waitUntilProgressIsDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 25_000) {
            composeTestRule.onNodeWithText("Loading...").isDisplayed()
        }
    }

    context(RobotTestRule)
    fun waitUntilWeatherIsDisplayed() {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onNodeWithTag("weather_list").isDisplayed()
        }
    }
    context(RobotTestRule)
    fun waitUntilWeatherIsLoaded(
        condition: String,
    ) {
        composeTestRule.waitUntil(timeoutMillis = 5_000) {
            composeTestRule.onNodeWithText(condition).isDisplayed()
        }
    }

    context(RobotTestRule)
    fun assertLoadingIsDisplayed() {
        composeTestRule.onNodeWithText("Loading...").assertIsDisplayed()
        composeTestRule.onNodeWithTag("loading").assertIsDisplayed()
    }

    context(RobotTestRule)
    fun assertWeatherList() {
        composeTestRule.onNodeWithTag("weather_list").assertIsDisplayed()
    }

    private fun getTempWithSign(temp: Float): String {
        return InstrumentationRegistry.getInstrumentation().targetContext.getString(
            com.example.weather.R.string.degrees_with_sign,
            temp,
        )
    }

    context(RobotTestRule)
    fun clickOnZurichLocationFromDrawer() {
        composeTestRule.onNodeWithText("Zurich")
            .assertIsDisplayed()
            .performClick()
    }

    context(RobotTestRule)
    fun assertDrawerIsOpen() {
        composeTestRule.onNodeWithTag("drawer_sheet")
            .assertIsDisplayed()
    }

    context(RobotTestRule)
    fun assertDrawerIsClosed() {
        composeTestRule.onNodeWithTag("drawer_sheet")
            .assertIsNotDisplayed()
    }

    context(RobotTestRule)
    fun clickOnDrawer() {
        composeTestRule.onNodeWithContentDescription(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                com.example.home.R.string.navigation_icon_content_description,
            ),
        ).assertIsDisplayed()
            .performClick()
    }

    context(RobotTestRule)
    fun assertTemperaturesAreDisplayed(
        weather: Weather,
    ) {
        composeTestRule.onNodeWithText(getTempWithSign(weather.temperature.current))
            .assertIsDisplayed()
        composeTestRule.onNodeWithText(
            InstrumentationRegistry.getInstrumentation().targetContext.getString(
                R.string.min_max_feelslike_placeholder,
                weather.temperature.max,
                weather.temperature.min,
                weather.temperature.feelsLike,
            ),
        ).assertIsDisplayed()
    }

    operator fun invoke(
        robotTestRule: RobotTestRule,
        function: context(RobotTestRule)
        MainActivityRobot.() -> Unit,
    ) {
        function(robotTestRule, this@MainActivityRobot)
    }
}
