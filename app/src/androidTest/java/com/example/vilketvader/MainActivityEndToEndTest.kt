package com.example.vilketvader

import androidx.compose.ui.test.onRoot
import androidx.compose.ui.test.printToLog
import androidx.test.core.app.ActivityScenario.launch
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.data.di.DatabaseModule
import com.example.data.di.LocationModule
import com.example.data.di.WeatherApiModule
import com.example.domain.LocationRepository
import com.example.domain.WeatherRepository
import com.example.testing.FakeLocationRepository
import com.example.testing.TestData
import com.example.testing.WeatherRepositoryFake
import dagger.hilt.android.testing.BindValue
import dagger.hilt.android.testing.HiltAndroidTest
import dagger.hilt.android.testing.UninstallModules
import javax.inject.Inject
import kotlinx.coroutines.runBlocking
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith


@RunWith(AndroidJUnit4::class)
@UninstallModules(WeatherApiModule::class, LocationModule::class, DatabaseModule::class)
@HiltAndroidTest
class MainActivityEndToEndTest {

    @get:Rule
    val robotTestRule = RobotTestRule(this)

    @Inject
    lateinit var robot: MainActivityRobot


    @BindValue
    @JvmField
    val weatherRepo: WeatherRepository = WeatherRepositoryFake(
        context = ApplicationProvider.getApplicationContext()
    )

    @BindValue
    @JvmField
    val locationRepository: LocationRepository = FakeLocationRepository()
    @Test
    fun testProgressIsShownAtLaunch() {
        launch(MainActivity::class.java)
        robot(robotTestRule) {
            assertLoadingIsDisplayed()
        }
    }
    @Test
    fun testAfterProgressWeatherIsShown() {
        launch(MainActivity::class.java)
        val stockholmWeather = runBlocking { weatherRepo.getWeather(TestData.STOCKHOLM, "en", false) }
        val zurich = runBlocking { weatherRepo.getWeather(TestData.ZURICH, "en", false) }
        robot(robotTestRule) {
            assertLoadingIsDisplayed()
            waitUntilWeatherIsDisplayed()
            assertWeatherList()
            composeTestRule.onRoot().printToLog("Weather list")
            assertTemperaturesAreDisplayed(stockholmWeather)
            //TODO assert more UI elements
            assertDrawerIsClosed()
            clickOnDrawer()
            assertDrawerIsOpen()
            clickOnZurichLocationFromDrawer()
            assertDrawerIsClosed()
        }
    }
}
