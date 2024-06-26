package com.example.home.drawer

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DismissibleDrawerSheet
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import com.example.home.HomeUiState

@Composable
internal fun MainDrawerSheet(
    state: HomeUiState,
    modifier: Modifier = Modifier,
    onDrawerItemClicked: (UiLocation) -> Unit,
) {
    DismissibleDrawerSheet(
        modifier = modifier.testTag("drawer_sheet"),
        drawerContainerColor = Color.Transparent,
    ) {
        Card(
            modifier = Modifier.fillMaxSize(),
            colors = CardDefaults.elevatedCardColors(
                containerColor = Color.White.copy(
                    0.1f,
                ),
            ),
        ) {
            DrawerLazyColumn(
                state = state,
                onDrawerItemClicked = onDrawerItemClicked,
            )
        }
    }
}
