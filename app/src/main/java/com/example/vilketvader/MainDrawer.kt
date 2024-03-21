package com.example.vilketvader

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import kotlinx.collections.immutable.persistentListOf
import kotlinx.coroutines.launch


val beforeSunrise = listOf(
    Color(0xFF85bce5),
    Color(0xFF84bce6),
    Color(0xFF87badc),
    Color(0xFF87bde2),
    Color(0xFF8cb9d0),
    Color(0xFF8cb9d0),
    Color(0xFFa6af9e),
    Color(0xFFb8a76f)
)

val sunnyColorsGradient = listOf(
    Color(0xff5699e0),
    Color(0xff5699df),
    Color(0xff589ade),
    Color(0xff589adf),
    Color(0xff5a9cd9),
    Color(0xff5c9dd7),
    Color(0xff5c9dd5),
    Color(0xff5f9ed3),
    Color(0xff6ba4d2),
    Color(0xff75a9d1),
)

@Composable
internal fun MainDrawer() {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()
    val items = persistentListOf(
        UiLocation("Mongo", isSelected = true),
        UiLocation("Kinlochleven", isSelected = false),
        UiLocation("Stockholm", isSelected = false),
        UiLocation("Amsterdam"),
        UiLocation("Zurich"),
        UiLocation("Accra"),
        UiLocation("Gothenburg"),
        UiLocation("London"),
        UiLocation("Mountain View"),
        UiLocation("Berlin"),
        UiLocation("New York"),
        UiLocation("Tehran"),
        UiLocation("Abadan"),
        UiLocation("Bijar"),
        UiLocation("Malmö"),
        UiLocation("Uppsala"),
        UiLocation("Västerås"),
        UiLocation("Örebro"),
    )
    var selectedItem by remember { mutableStateOf(items[0]) }

    fun openDrawer() { scope.launch { drawerState.open() } }
    fun closeDrawer() { scope.launch { drawerState.close() } }
    BackHandler(enabled = drawerState.isOpen, onBack = ::openDrawer)

    Box {
        DismissibleNavigationDrawer(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(sunnyColorsGradient)),
            drawerState = drawerState,
            drawerContent = {
                MainDrawerSheet(
                    items = items,
                    selectedItem = selectedItem,
                    onDrawerItemClicked = {
                        selectedItem = it
                        closeDrawer()
                    }
                )
            },
            content = {
                MainContent(
                    selectedItem = selectedItem,
                    onDrawerClicked = ::openDrawer
                )
            }
        )
    }

}

@Composable
private fun MainContent(
    selectedItem: UiLocation,
    onDrawerClicked: () -> Unit
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                selectedCity = selectedItem,
                onDrawerOpenClicked = onDrawerClicked
            )
        },
        content = {
            WeatherScreenUi(
                modifier = Modifier,
                location = selectedItem,
                paddingValues = it
            )
        }
    )
}
