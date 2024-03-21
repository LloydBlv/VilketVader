package com.example.vilketvader

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainTopAppBar(
    selectedCity: UiLocation,
    onDrawerOpenClicked: () -> Unit
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White
        ),
        title = { TitleText(selectedCity, onDrawerOpenClicked) },
        navigationIcon = { NavigationIcon(onDrawerOpenClicked) })
}

@Composable
private fun NavigationIcon(onDrawerOpenClicked: () -> Unit) {
    IconButton(onClick = onDrawerOpenClicked) {
        Icon(
            tint = Color.White,
            imageVector = Icons.Default.Menu,
            contentDescription = null
        )
    }
}

@Composable
private fun TitleText(
    selectedCity: UiLocation,
    onDrawerOpenClicked: () -> Unit
) {
    Text(
        text = selectedCity.cityName,
        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
        modifier = Modifier.clickable(onClick = onDrawerOpenClicked)
    )
}