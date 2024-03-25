package com.example.home.drawer

import android.annotation.SuppressLint
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.DismissibleNavigationDrawer
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import com.example.home.HomeUiEvents
import com.example.home.HomeUiState
import com.example.screens.HomeScreen
import com.example.screens.WeatherScreen
import com.slack.circuit.codegen.annotations.CircuitInject
import com.slack.circuit.foundation.CircuitContent
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.launch

val beforeSunrise = listOf(
    Color(0xFF85bce5),
    Color(0xFF84bce6),
    Color(0xFF87badc),
    Color(0xFF87bde2),
    Color(0xFF8cb9d0),
    Color(0xFF8cb9d0),
    Color(0xFFa6af9e),
    Color(0xFFb8a76f),
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
@CircuitInject(HomeScreen::class, SingletonComponent::class)
fun HomeScreenUi(state: HomeUiState, modifier: Modifier = Modifier) {
    val eventSink = state.evenSink
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val scope = rememberCoroutineScope()

    fun openDrawer() {
        scope.launch { drawerState.open() }
    }
    fun closeDrawer() {
        scope.launch { drawerState.close() }
    }

    BackHandler(enabled = drawerState.isOpen, onBack = ::openDrawer)

    Box {
        DismissibleNavigationDrawer(
            modifier = Modifier
                .fillMaxSize()
                .background(Brush.verticalGradient(sunnyColorsGradient)),
            drawerState = drawerState,
            drawerContent = {
                MainDrawerSheet(
                    state = state,
                    onDrawerItemClicked = {
                        eventSink.invoke(HomeUiEvents.OnLocationSelected(it))
                        closeDrawer()
                    },
                )
            },
            content = {
                MainContent(
                    state = state,
                    onDrawerClicked = ::openDrawer,
                )
            },
        )
    }
}

@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
private fun MainContent(
    state: HomeUiState,
    onDrawerClicked: () -> Unit,
) {
    Scaffold(
        containerColor = Color.Transparent,
        contentColor = Color.White,
        modifier = Modifier.fillMaxSize(),
        topBar = {
            MainTopAppBar(
                state = state,
                onDrawerOpenClicked = onDrawerClicked,
            )
        },
    ) {
        CircuitContent(
            screen = WeatherScreen,
            modifier = Modifier.fillMaxSize().padding(it),
        )
    }
}
