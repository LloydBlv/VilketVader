package com.example.weather

import androidx.compose.ui.test.junit4.createComposeRule
import androidx.compose.ui.test.onRoot
import com.example.testing.ScreenshotTesting
import com.github.takahirom.roborazzi.RobolectricDeviceQualifiers
import com.github.takahirom.roborazzi.RoborazziRule
import com.github.takahirom.roborazzi.captureRoboImage
import kotlin.time.Duration.Companion.seconds
import org.junit.Rule
import org.junit.Test
import org.junit.experimental.categories.Category
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import org.robolectric.annotation.GraphicsMode

@GraphicsMode(GraphicsMode.Mode.NATIVE)
@RunWith(RobolectricTestRunner::class)
@Config(sdk = [32], qualifiers = RobolectricDeviceQualifiers.MediumPhone)
class WeatherUiScreenshotTest {
    @get:Rule
    val composeTestRule = createComposeRule()

    @get:Rule
    val roborazziRule = RoborazziRule(
        composeRule = composeTestRule,
        captureRoot = composeTestRule.onRoot(),
        options = RoborazziRule.Options(
            outputDirectoryPath = "src/androidUnitTest/snapshots/images",
        ),
    )

    @Test
    @Category(ScreenshotTesting::class)
    fun weatherScreenLoadingStateTest() {
        composeTestRule.mainClock.autoAdvance = false
        composeTestRule.setContent {
            WeatherScreenUi(state = WeatherUiState.Loading)
        }
        composeTestRule.mainClock.advanceTimeBy(2.seconds.inWholeMilliseconds)
        composeTestRule.onRoot()
            .captureRoboImage()
    }

    @Test
    @Category(ScreenshotTesting::class)
    fun weatherScreenFailedStateTest() {
        composeTestRule.setContent {
            WeatherScreenUi(state = WeatherUiState.Failure(error = Throwable("Network error"), {}))
        }
        composeTestRule.onRoot()
            .captureRoboImage()
    }
}
