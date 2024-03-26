package com.example.home.drawer

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
import androidx.compose.ui.res.stringResource
import com.example.home.HomeUiState
import com.example.home.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun MainTopAppBar(
    state: HomeUiState,
    onDrawerOpenClicked: () -> Unit,
) {
    TopAppBar(
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color.Transparent,
            titleContentColor = Color.White,
        ),
        title = { TitleText(state, onDrawerOpenClicked) },
        navigationIcon = { NavigationIcon(onDrawerOpenClicked) },
    )
}

@Composable
private fun NavigationIcon(onDrawerOpenClicked: () -> Unit) {
    IconButton(onClick = onDrawerOpenClicked) {
        Icon(
            tint = Color.White,
            imageVector = Icons.Default.Menu,
            contentDescription = stringResource(id = R.string.navigation_icon_content_description),
        )
    }
}

@Composable
private fun TitleText(
    state: HomeUiState,
    onDrawerOpenClicked: () -> Unit,
) {
    val uiLocation = state.locations.find { it.isSelected }
    Text(
        text = uiLocation?.cityName ?: "Loading",
        style = MaterialTheme.typography.headlineSmall.copy(color = Color.White),
        modifier = Modifier.clickable(onClick = onDrawerOpenClicked),
    )
}
